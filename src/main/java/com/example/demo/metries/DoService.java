package com.example.demo.metries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author ljm
 * @date 2023/9/12 14:42
 */
@Service
@Slf4j
public class DoService {
    public CompletableFuture<Void> reAdd(){
        return CompletableFuture.runAsync(()->{
            List<String> strings = new ArrayList<>();
            while (true){
                strings.add("aaaaaa");
//                log.info("add");
            }
        });
    }
}
