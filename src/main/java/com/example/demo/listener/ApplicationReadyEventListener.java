package com.example.demo.listener;

import com.example.demo.application.ApplicationContextRegister;
import com.example.demo.metries.DoService;
import com.example.demo.metries.MeterService;
import com.example.demo.metries.ReRunService;
import com.example.demo.redis.RedisAddService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author ljm
 * @date 2023/9/1 15:17
 */
@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ApplicationReadyEventListener.onApplicationEvent.start");
        CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        ApplicationContextRegister.getBean(RedisAddService.class).add1();
        ApplicationContextRegister.getBean(RedisAddService.class).add2();
        log.info("ApplicationReadyEventListener.onApplicationEvent.end");
    }
}
