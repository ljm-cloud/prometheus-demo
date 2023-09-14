package com.example.demo.metries;

import com.example.demo.application.ApplicationContextRegister;
import com.example.demo.helper.ThreadPoolHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.example.demo.helper.ThreadPoolHelper.commonScheduledThreadPool;

/**
 * @author ljm
 * @date 2023/9/11 10:21
 */
@Service
@Slf4j
public class ReRunService {
    private List<Object> objects = new ArrayList<>();
    public CompletableFuture<Void> count(){
        return CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            commonScheduledThreadPool.scheduleAtFixedRate(()->{
                for (int i = 0; i < 100; i++) {
                    ThreadPoolHelper threadPoolHelper = new ThreadPoolHelper();
                    threadPoolHelper.toString();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("ReRunService.scheduleAtFixedRate.new ThreadPoolHelper");
            },0,1, TimeUnit.NANOSECONDS);
            System.out.println("ReRunService.scheduleAtFixedRate.pass....");
            CompletableFuture.runAsync(()->{
                while (true){
                    ReRunService reRunService = new ReRunService();
                    try {
                        Object o = reRunService.clone();
                        objects.add(o);
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("ReRunService.ReRunService.clone");
                }
            });
            CompletableFuture.runAsync(()->{
                while (true){
                    String stringBuilder = "aaa".repeat(1);
                    ApplicationContextRegister.getBean(MeterService.class).list.add(stringBuilder);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("ReRunService.MeterService.list.add");
                }
            });
            CompletableFuture.runAsync(()->{
                ApplicationContextRegister.getBean(MeterService.class).integers = new ArrayList<>();
                while (true){
                    CompletableFuture.runAsync(()->{
                        for (int i = 0; i < 1000; i++) {
                            try {
                                ApplicationContextRegister.getBean(MeterService.class).integers.add(i);
                            }catch (Exception e){
                                log.error("integers.add.error",e);

                            }
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        }
                        log.info("ReRunService.MeterService.integers.add");
                    });
                }
            });
            CompletableFuture.runAsync(()->{
                while (true){
                    for (int i = 0; i < 10; i++) {
                        try {
                            Iterator<Object> iterator = objects.iterator();
                            while (iterator.hasNext()){
                                iterator.remove();
                            }
                        }catch (Exception e){
                            log.error("objects.remove.error",e);
                        }
                        try {
                            Iterator<Integer> iterator = ApplicationContextRegister.getBean(MeterService.class).integers.iterator();
                            while (iterator.hasNext()){
                                iterator.remove();
                            }
                        }catch (Exception e){
                            log.error("integers.remove.error",e);
                        }

                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("ReRunService.iterator.remove");
                }
            });
        });
    }
}
