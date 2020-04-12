package com.vinspier.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GoodsWebApplication {

    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(10);
    }

    public static void main(String[] args) {
        SpringApplication.run(GoodsWebApplication.class,args);
    }
}
