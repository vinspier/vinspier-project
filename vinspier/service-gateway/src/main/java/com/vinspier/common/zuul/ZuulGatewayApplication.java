package com.vinspier.common.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: ZuulGatewayApplication
 * @Description:
 * @Author:
 * @Date: 2020/3/20 13:42
 * @Version V1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ZuulGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class,args);
    }
}
