package com.blog.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

import lombok.Data;

/**
 * GatewayConfigProperties
 * <p>
 * 网关配置文件
 *
 * @author Wenzhou
 * @since 2023/5/11 10:48
 */
@Data
@RefreshScope
@ConfigurationProperties("gateway")
public class GatewayConfigProperties {
    /**
     * 网关解密登录前端密码 密钥 {@link com.blog.gateway.filter.PasswordDecoderFilter}
     */
    private String encodeKey;

    /**
     * 网关不需要校验验证码的客户端 {@link com.blog.gateway.filter.ValidateCodeGatewayFilter}
     */
    private List<String> ignoreClients;
}
