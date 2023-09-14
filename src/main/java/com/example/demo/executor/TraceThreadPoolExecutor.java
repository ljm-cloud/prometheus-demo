package com.example.demo.executor;

import com.example.demo.application.ApplicationContextRegister;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import org.slf4j.MDC;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

    private final String name;

    public TraceThreadPoolExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.name = name;
        ExecutorServiceMetrics.monitor(ApplicationContextRegister.getBean(MeterRegistry.class),this,name);
    }

    @Override
    public void execute(Runnable command) {
        Map<String, String> map = MDC.getCopyOfContextMap();
        super.execute(()-> {
            if(map!=null && !map.isEmpty()){
                MDC.setContextMap(map);
            }
            command.run();
        });
    }

    public String getName(){
        return name;
    }
}
