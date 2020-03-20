package com.vinspier.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassName: ZuulGatewayApplication
 * @Description:
 * @Author:
 * @Date: 2020/3/20 13:42
 * @Version V1.0
 **/
@SpringBootApplication
@EnableZuulProxy // 开启网关功能
@EnableDiscoveryClient
public class ZuulGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class,args);
    }
}
