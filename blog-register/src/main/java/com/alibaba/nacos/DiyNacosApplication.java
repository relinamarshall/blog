package com.alibaba.nacos;

import com.alibaba.nacos.config.ConfigConstants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

/**
 * DiyNacosApplication
 * <p>
 * nacos console 源码运行，方便开发 生产从官网下载zip最新版集群配置运行
 *
 * @author Wenzhou
 * @since 2023/5/10 14:13
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class DiyNacosApplication {
    public static void main(String[] args) {
        if (initEnv()) {
            SpringApplication.run(DiyNacosApplication.class, args);
        }
    }

    /**
     * 初始化运行环境
     */
    private static boolean initEnv() {
        // 修改nacos启动端口
        System.setProperty("server.port", "9998");
        System.setProperty("server.servlet.contextPath", "/nacos");
        System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
        System.setProperty(ConfigConstants.AUTH_ENABLED, "true");
        System.setProperty(ConfigConstants.LOG_BASEDIR, "logs");
        System.setProperty(ConfigConstants.LOG_ENABLED, "false");
        return true;
    }

}
