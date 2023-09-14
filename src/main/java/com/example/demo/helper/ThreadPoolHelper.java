package com.example.demo.helper;

import com.example.demo.executor.NamedThreadFactory;
import com.example.demo.executor.TraceThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ljm
 * @date 2023/9/4 17:41
 */
public class ThreadPoolHelper {
    public static final TraceThreadPoolExecutor redisAddExecutor = new TraceThreadPoolExecutor("redisAdd",Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("redisAdd"));

    public static ScheduledExecutorService commonScheduledThreadPool = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());

}
