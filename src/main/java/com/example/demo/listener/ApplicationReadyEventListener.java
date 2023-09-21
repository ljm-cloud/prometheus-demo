package com.example.demo.listener;

import com.example.demo.application.ApplicationContextRegister;
import com.example.demo.cache.LocalCacheService;
import com.example.demo.metries.ExecutorService;
import com.example.demo.metries.MeterService;
import com.example.demo.redis.RedisAddService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
        ApplicationContextRegister.getBean(RedisAddService.class).add1();
        ApplicationContextRegister.getBean(RedisAddService.class).add2();
        ApplicationContextRegister.getBean(LocalCacheService.class).flushCache();
        ApplicationContextRegister.getBean(ExecutorService.class).run();
        ApplicationContextRegister.getBean(MeterService.class).counter();
        ApplicationContextRegister.getBean(MeterService.class).gauge();
        log.info("ApplicationReadyEventListener.onApplicationEvent.end");
    }
}
