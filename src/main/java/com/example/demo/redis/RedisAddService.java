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
    private StringRedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate2;

    public CompletableFuture<Void> add1(){
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(()->{
            while (true){
                String key = String.valueOf(RandomUtils.nextInt(1000000));
                redisTemplate.opsForValue().set(key,
                        "aaaaa",1, TimeUnit.MINUTES);
                log.info("redis.add|key:{}",key);
            }
        }, ThreadPoolHelper.redisAddExecutor);
        CompletableFuture.runAsync(()->{
            while (true){
                String key = String.valueOf(RandomUtils.nextInt(1000000));
                redisTemplate.opsForValue().get(key);
            }
        }, ThreadPoolHelper.redisAddExecutor);
        completableFuture.complete(null);
        return completableFuture;
    }
    public CompletableFuture<Void> add2(){
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(()->{
            int i = 0;
            while (true){
                redisTemplate2.opsForZSet().add(String.valueOf(i),"",System.currentTimeMillis());
                redisTemplate2.expire(String.valueOf(i),30,TimeUnit.SECONDS);
                i ++;
            }
        }, ThreadPoolHelper.redisAddExecutor);
        CompletableFuture.runAsync(()->{
            int i = 0;
            while (true){
                redisTemplate2.delete(String.valueOf(i));
                i++;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.error("RedisAddService.add2.sleep.error",e);
                }
            }
        }, ThreadPoolHelper.redisAddExecutor);
        completableFuture.complete(null);
        return completableFuture;
    }
}
