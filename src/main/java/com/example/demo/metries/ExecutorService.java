package com.example.demo.metries;

import com.example.demo.helper.ThreadPoolHelper;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author ljm
 * @date 2023/9/21 14:52
 */
@Service
public class ExecutorService {
    public CompletableFuture<Void> run(){
        return CompletableFuture.runAsync(()->{
            while (true){
                ThreadPoolHelper.meterExecutor.execute(()->{
                    try {
                        Thread.sleep(RandomUtils.nextInt(10000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
