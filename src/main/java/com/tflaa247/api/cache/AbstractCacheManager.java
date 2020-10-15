package com.tflaa247.api.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import com.tflaa247.api.config.CacheConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCacheManager<T> implements InitializingBean {

    private static final int SCHEDULE_TIME_MILLISECOND = 600_000;

    private static final String CATEGORY_CACHE_KEY = "com.tflaa247.api.cache.CategoryCache";
    private static final String PRODUCT_CACHE_KEY = "com.tflaa247.api.cache.ProductCache";

    @Autowired
    CacheConfig cacheConfig;

    Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    protected abstract T initialLoad();

    protected abstract Object reload(long key);

    protected abstract T reloadAll(List<Long> keyNos);

    @Override
    public void afterPropertiesSet() throws Exception {
        cache();
    }

    // 초기 Cache 설정 (CategoryCache / ProductCache)
    protected void cache() {
        String className = ClassUtils.getUserClass(getClass()).getName();
        Object data = this.initialLoad();
        if (data == null) {
            log.warn("{}.load() returned null. Caching skipped.", className);
            return;
        }

        LRUCache cache = null;
        // ClassName을 Key로 설정하고 데이터 정보를 LRUCache 객체에 담에 Value로 설정
        if (CATEGORY_CACHE_KEY.equals(className)) {
            // CategoryCache 설정
            cache = cacheConfig.categoryLruCache();
        } else if (PRODUCT_CACHE_KEY.equals(className)) {
            // ProductCache 설정
            cache = cacheConfig.productLruCache();
        }
        // 적재된 데이터를 LRUCache 객체에 설정
        cache.setAll(data);
        // 캐시에 데이터 적재
        cacheMap.put(className, cache);
    }

    /**
     * 캐싱된 데이터 리스트 조회
     *
     * @return
     */
    public T getList() {
        String className = ClassUtils.getUserClass(getClass()).getName();
        return (T) get(className, null);
    }

    /**
     * 캐싱된 데이터 조회(LRUCache Key 전달)
     *
     * @param key
     * @return
     */
    public T get(long key) {
        String className = ClassUtils.getUserClass(getClass()).getName();
        return (T) get(className, key);
    }

    protected Object get(String className, Long key) {
        // cacheMap 조회 (key = className)
        LRUCache lruCache = (LRUCache) cacheMap.get(className);
        if (ObjectUtils.isEmpty(key)) {
            log.warn("{} hit.", className);
            return lruCache.getAll();
        }
        // 전달받은 key(categoryNo, productNo)로 데이터 조회
        Object data = lruCache.get(key);
        if (!ObjectUtils.isEmpty(data)) {  // cache hit
            log.warn("{}:{} cache hit.", className, key);
            return data;
        }
        // Cache Hit가 되지 않은 경우, 해당 데이터가 DB에 존재하는지 확인
        // 만약 DB에도 존재하지 않는다면 캐싱할 필요가 없기 때문에 null 처리
        Object reloadData = this.reload(key);
        if (ObjectUtils.isEmpty(reloadData)) {  // 저장된 데이터가 없는 경우
            log.warn("no saved data.");
            return null;
        }

        log.warn("{}:{} cache miss.", className, key);

        // cache miss가 발생한 경우
        // LRU 알고리즘으로 가장 최근에 호출된 적이 없는 key(categoryNo / productNo)를 교체
        // 교체 후 Cache에 해당 데이터 적재
        LRUCache cache = null;
        if (CATEGORY_CACHE_KEY.equals(className)) {
            cache = cacheConfig.categoryLruCache();
        } else if (PRODUCT_CACHE_KEY.equals(className)) {
            cache = cacheConfig.productLruCache();
        }

        cache.set(key, reloadData);
        cacheMap.put(className, cache);

        return reloadData;
    }

    @Scheduled(fixedDelay = SCHEDULE_TIME_MILLISECOND, initialDelay = SCHEDULE_TIME_MILLISECOND)
    public void optimizeCategoryCache() {
        optimizeCache(CATEGORY_CACHE_KEY);
    }

    @Scheduled(fixedDelay = SCHEDULE_TIME_MILLISECOND, initialDelay = SCHEDULE_TIME_MILLISECOND)
    public void optimizeProductCache() {
        optimizeCache(PRODUCT_CACHE_KEY);
    }

    /**
     * cache의 value 값을 갱신을 하기 위해서
     * cache에 담겨져있는 값(key)을 기준으로 DB 조회하도록 설계
     * Cron으로 10분마다 cache의 Value값 수정
     *
     * @param key
     */
    public void optimizeCache(String key) {
        LRUCache lruCache = (LRUCache) cacheMap.get(key);
        if (Objects.nonNull(lruCache)) {
            Map<Long, Object> lruCacheMap = (Map<Long, Object>) lruCache.getAll();
            List<Long> presentNoList = new ArrayList<>(lruCacheMap.keySet());

            LRUCache cache = null;
            if (CATEGORY_CACHE_KEY.equals(key)) {
                cache = cacheConfig.categoryLruCache();
            } else if (PRODUCT_CACHE_KEY.equals(key)) {
                cache = cacheConfig.productLruCache();
            }

            Object data = this.reloadAll(presentNoList);
            // 적재된 데이터를 LRUCache 객체에 설정
            cache.setAll(data);
            // 캐시에 데이터 적재
            cacheMap.put(key, cache);
        }
    }
}
