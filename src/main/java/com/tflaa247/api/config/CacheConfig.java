package com.tflaa247.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tflaa247.api.cache.LRUCache;

@Configuration
public class CacheConfig {

    private static final int CATEGORY_CACHE_SIZE = 10;
    private static final int PRODUCT_CACHE_SIZE = 1000;

    @Bean
    public LRUCache categoryLruCache() {
        return new LRUCache(CATEGORY_CACHE_SIZE);
    }

    @Bean
    public LRUCache productLruCache() {
        return new LRUCache(PRODUCT_CACHE_SIZE);
    }
}
