package com.example.demo.aop;

import com.example.demo.application.ApplicationContextRegister;
import com.example.demo.metries.MeterService;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ljm
 * @date 2023/9/21 10:58
 */
@Aspect
@Component
@Slf4j
public class WebCostAspect {
    @Pointcut("execution( * com.example.demo.controller.*.*(..))")
    public void costPointCut(){

    }

    @Around("costPointCut()")
    public Object doAround(ProceedingJoinPoint pjp){
        MeterService meterService = ApplicationContextRegister.getBean(MeterService.class);
        long startTime = System.currentTimeMillis();
        Timer.Sample sample = Timer.start();
        Object obj = meterService.timer(()->{
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                log.error("WebCostAspect.doAround.pjp.proceed.error",e);
                return null;
            }
        });
        meterService.timeSample(sample);
        long endTime = System.currentTimeMillis();
        meterService.timerUnit(endTime-startTime, TimeUnit.MILLISECONDS);
        return obj;
    }
}
