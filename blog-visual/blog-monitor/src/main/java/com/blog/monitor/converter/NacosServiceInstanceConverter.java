package com.blog.monitor.converter;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

import de.codecentric.boot.admin.server.cloud.discovery.DefaultServiceInstanceConverter;

import static java.util.Collections.emptyMap;

/**
 * NacosServiceInstanceConverter
 * <p>
 * 针对 nacos 2.x 服务注册处理
 *
 * @author Wenzhou
 * @since 2023/5/10 14:59
 */
@Configuration(proxyBeanMethods = false)
public class NacosServiceInstanceConverter extends DefaultServiceInstanceConverter {

    @Override
    protected Map<String, String> getMetadata(ServiceInstance instance) {
        return (instance.getMetadata() != null) ? instance.getMetadata()
                .entrySet()
                .stream()
                .filter(e -> e.getKey() != null && e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)) : emptyMap();
    }
}
