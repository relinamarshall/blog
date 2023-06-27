package com.blog.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log
 *
 * @author Wenzhou
 * @since 2023/6/19 17:20
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 描述
     *
     * @return {String}
     */
    String value() default "";

    /**
     * spel 表达式
     *
     * @return 日志描述
     */
    String expression() default "";
}
