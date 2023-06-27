package com.blog.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * MonitorApplication
 *
 * @author Wenzhou
 * @since 2023/6/21 20:50
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
