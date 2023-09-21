package com.example.demo.metries;

import com.example.demo.helper.ThreadPoolHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ljm
 * @date 2023/9/5 17:04
 */
@Service
@Slf4j
public class MeterService {

    @Autowired
    private MeterRegistry meterRegistry;
    private Integer integer = 0;
    public List<String> list = new ArrayList<>();
    List<Integer> integers;
    private Map<String,String> map;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    public CompletableFuture<Void> counter(){
        return CompletableFuture.runAsync(()->{
            while (true){
                Counter counter = meterRegistry.counter("counter_increment");
                counter.increment();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },ThreadPoolHelper.meterExecutor);
    }

    public CompletableFuture<Void> gauge(){
        return CompletableFuture.runAsync(()->{
            meterRegistry.gauge("list_size_gauge", Collections.emptyList(),list, List::size);
            integers = meterRegistry.gaugeCollectionSize("collection_size_gauge", Tags.empty(), Lists.newArrayList());
            map = Maps.newHashMap();
            meterRegistry.gaugeMapSize("map_size_gauge",Tags.empty(),map);
            meterRegistry.gauge("atomic_integer_gauge",atomicInteger);
            while (true){
                list.add("00");
                integers.add(1);
                map.put("aa","aa");
                int i = atomicInteger.getAndIncrement();
                if (i%30==0){
                    //每增加到30就归0
                    atomicInteger.set(0);
                }
                //测试每次传Integer数，gauge会不会变化
                meterRegistry.gauge("random_int_gauge", RandomUtils.nextInt(1000));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },ThreadPoolHelper.meterExecutor);
    }

    public CompletableFuture<Void> addCounter(){
        meterRegistry.gauge("list_size_gauge", Collections.emptyList(),list, List::size);
        integers = meterRegistry.gaugeCollectionSize("collection_size_gauge", Tags.empty(),new ArrayList<Integer>());
        Timer timer = meterRegistry.timer("time_consume");
        map = new HashMap<>();
        meterRegistry.gaugeMapSize("map_size_gauge",Tags.empty(),map);
        meterRegistry.gauge("atomic_integer_gauge",atomicInteger);
        return CompletableFuture.runAsync(()->{
            while (true){
                Counter counter = meterRegistry.counter("people_count");
                counter.increment();
                log.info("counter.increment()|{}|{}",counter,counter.count());
                integer ++ ;
                if (integer%2==0){
                    Integer d  = meterRegistry.gauge("people_gauge",2);
                }else {
                    Integer d  = meterRegistry.gauge("people_gauge",3);
                }
                timer.record(()->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                Timer.Sample sample = Timer.start();
                CompletableFuture.runAsync(()->{
                    new Thread(()->{
                        try {
                            Thread.sleep(5000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        sample.stop(meterRegistry.timer("time_consume_sample"));
                    }).start();
                });
                list.add("00");
                map.put("aa","aa");
                for (int i = 0; i < 2; i++) {
                    integers.add(1);
                    map.put(String.valueOf(i),"i");
                }
                int i = atomicInteger.getAndIncrement();
                if (i%10==0){
                    atomicInteger.set(5);
                }
//                for (int j = 0; j <10000; j++) {
//                    CompletableFuture.runAsync(()->{
//                        for (int k = 0; k < 100000000 ; k++) {
//                            ThreadPoolHelper threadPoolHelper = new ThreadPoolHelper();
//                            threadPoolHelper.toString();
//                        }
//                    });
//                }
//                log.info("counter.gauge()|{}",d);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, ThreadPoolHelper.meterExecutor);
    }
}
