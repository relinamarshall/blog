package com.blog.common.core.job.annotation;

import com.blog.common.core.job.XxlJobAutoConfiguration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableXxlJob
 * <p>
 * 激活xxl-job配置
 *
 * @author Wenzhou
 * @since 2023/6/19 15:31
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({XxlJobAutoConfiguration.class})
public @interface EnableXxlJob {
}
