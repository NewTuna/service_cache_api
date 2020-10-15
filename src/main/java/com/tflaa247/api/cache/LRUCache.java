package com.tflaa247.api.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {

    private LinkedHashMap<Long, Object> cacheMap;

    /**
     * CacheEviction 처리를 위한 LRUCache Class 생성
     * 현재 구현한 CacheService Api에는 Cache 사이즈를 제한해두었음 (CategoryCacheSize : 10 / ProductCacheSize : 1000)
     * 해당 Cache 사이즈를 무한정으로 줄 수 없기때문에 초기 기동시 원본 DB에 저장되어있는 개수만큼으로 사이즈를 정함
     * Cache 사이즈는 한정되어 있기 때문에 가장 최근에 사용되지 않은 데이터를 우선적으로 캐시에서 배제하기 위한 정책 설정 (LRU 알고리즘)
     * 사용자가 관심있거나 선호하는 데이터에 대해서는 빈번한 접근이 발생하기 때문에 해당 데이터에 대해서는 캐싱을 적용하는 것이 적절하다가 판단
     * @param capacity
     */
    public LRUCache(int capacity) {
        cacheMap = new LinkedHashMap<Long, Object>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    /**
     * 캐싱된 모든 데이터 조회
     * @return
     */
    public Object getAll() {
        return cacheMap;
    }

    /**
     * Cache Key로 데이터 조회
     * @param key
     * @return
     */
    public Object get(long key) {
        return cacheMap.getOrDefault(key, null);
    }

    /**
     * cache miss 발생했을 경우
     * 가장 최근에 사용되지 않은 데이터를 새로 입력받은 key / value로 변경
     * @param key
     * @param value
     */
    public void set(long key, Object value) {
        cacheMap.put(key, value);
    }

    /**
     * Cache 데이터 초기화
     * @param data
     */
    public void setAll(Object data) {
        cacheMap.putAll((Map<? extends Long, ?>) data);
    }
}
