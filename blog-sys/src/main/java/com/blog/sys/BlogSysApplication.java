package com.blog.sys;

import com.blog.common.core.job.annotation.EnableXxlJob;
import com.blog.common.core.swagger.annotation.EnableDoc;
import com.blog.common.feign.annotation.EnableFeignClients;
import com.blog.common.security.annotation.EnableResourceServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * BlogSysApplication
 *
 * @author Wenzhou
 * @since 2023/6/23 12:57
 */
@EnableDoc
@EnableXxlJob
@EnableResourceServer
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class BlogSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogSysApplication.class, args);
    }
}
