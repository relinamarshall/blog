package com.blog.common.security.annotation;


import com.blog.common.security.configuration.DiyResourceServerAutoConfiguration;
import com.blog.common.security.configuration.DiyResourceServerConfiguration;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableResourceServer
 * <p>
 * 资源服务注解
 *
 * @author Wenzhou
 * @since 2023/5/8 16:18
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({DiyResourceServerAutoConfiguration.class, DiyResourceServerConfiguration.class})
public @interface EnableResourceServer {

}