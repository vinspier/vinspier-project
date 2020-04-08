package com.vinspier.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: SearchApplication
 * @Description: 搜索服务启动类
 * @Author:
 * @Date: 2020/4/8 16:20
 * @Version V1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }

}
