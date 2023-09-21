package com.example.demo.controller;

import com.example.demo.resp.RestFulResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author ljm
 * @date 2023/9/21 10:45
 */
@RestController
@RequestMapping("meter")
@Slf4j
public class MeterController {

    @GetMapping("/sleep")
    public ResponseEntity<RestFulResponse> sleep(){
        int time = RandomUtils.nextInt(5);
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            log.error("MeterController.sleep.TimeUnit.SECONDS.sleep.error",e);
        }
        return ResponseEntity.ok(RestFulResponse.ok());
    }
}
