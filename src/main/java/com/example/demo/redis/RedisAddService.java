package com.example.demo.redis;

import com.example.demo.helper.ThreadPoolHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ljm
 * @date 2023/9/1 15:13
 */
@Service
@Slf4j
public class RedisAddService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public CompletableFuture<Void> add(){
        return CompletableFuture.runAsync(()->{
            String key = String.valueOf(RandomUtils.nextInt(1000000));
            stringRedisTemplate.opsForValue().set(key,
                    "aaaaa",1, TimeUnit.HOURS);
            log.info("redis.add|key:{}",key);
        }, ThreadPoolHelper.redisAddExecutor);
    }
}
