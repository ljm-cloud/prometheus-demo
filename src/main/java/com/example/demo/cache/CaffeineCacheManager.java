package com.example.demo.cache;

import com.example.demo.application.ApplicationContextRegister;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ljm
 * @date 2023/9/21 15:35
 */
@Component
public class CaffeineCacheManager {
    public Cache<String,String> buildDefaultCaffeineCache(String cacheName){
        Cache<String,String> cache =  Caffeine.newBuilder()
                .expireAfterWrite(25, TimeUnit.SECONDS)//设置最后一次写入经过固定时间过期
                .recordStats()//启用缓存的统计信息记录功能
                .maximumSize(10000)//缓存最大条数
                .initialCapacity(100)// 初始的缓存空间大小
                .build();
        monitor(cache,cacheName);
        return cache;
    }

    public void monitor(Cache cache,String cacheName){
        CaffeineCacheMetrics.monitor(ApplicationContextRegister.getBean(MeterRegistry.class), cache, cacheName);
    }
}
