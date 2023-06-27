package com.blog.xxl.job.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * XxlJobAdminApplication
 *
 * @author Wenzhou
 * @since 2023/5/10 20:34
 */
@EnableDiscoveryClient
@SpringBootApplication
public class XxlJobAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(XxlJobAdminApplication.class, args);
    }
}
