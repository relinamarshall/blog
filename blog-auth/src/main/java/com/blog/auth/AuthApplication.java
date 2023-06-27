package com.blog.auth;


import com.blog.common.feign.annotation.EnableFeignClients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AuthApplication
 * <p>
 * 认证授权中心
 *
 * @author Wenzhou
 * @since 2023/5/11 13:47
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
