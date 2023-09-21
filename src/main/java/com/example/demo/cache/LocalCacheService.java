package com.example.demo.cache;

import com.example.demo.helper.ThreadPoolHelper;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ljm
 * @date 2023/9/21 15:47
 */
@Service
@Slf4j
public class LocalCacheService {

    @Autowired
    private CaffeineCacheManager caffeineCacheManager;

    Cache<String,String> cache;
    public void initCache(){
        cache = caffeineCacheManager.buildDefaultCaffeineCache("flush");
    }

    public CompletableFuture<Void> flushCache(){
        return CompletableFuture.runAsync(()->{
            initCache();
            ThreadPoolHelper.commonScheduledThreadPool.scheduleAtFixedRate(()->{
                cache.put(String.valueOf(RandomUtils.nextInt(50)),"");
            },0,2, TimeUnit.SECONDS);
            ThreadPoolHelper.commonScheduledThreadPool.scheduleAtFixedRate(()->{
                cache.get(String.valueOf(RandomUtils.nextInt(50)),k->"");
            },0,2,TimeUnit.SECONDS);
        });
    }
}
