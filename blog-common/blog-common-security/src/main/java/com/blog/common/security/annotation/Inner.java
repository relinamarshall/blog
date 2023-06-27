package com.blog.common.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inner
 * <p>
 * 服务调用不鉴权注解
 *
 * @author Wenzhou
 * @since 2023/5/8 16:23
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inner {

    /**
     * 是否AOP统一处理
     *
     * @return false, true
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段(预留)
     *
     * @return {}
     */
    String[] field() default {};

}
