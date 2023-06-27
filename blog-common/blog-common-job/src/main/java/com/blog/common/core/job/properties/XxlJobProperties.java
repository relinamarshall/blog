package com.blog.common.core.job.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Data;

/**
 * XxlJobProperties
 * <p>
 * xxl-job配置
 *
 * @author Wenzhou
 * @since 2023/6/19 15:31
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    /**
     * @NestedConfigurationProperty 标注是外部类配置
     * <p>
     * xxl.job.admin.addresses
     */
    @NestedConfigurationProperty
    private XxlAdminProperties admin = new XxlAdminProperties();

    @NestedConfigurationProperty
    private XxlExecutorProperties executor = new XxlExecutorProperties();
}
