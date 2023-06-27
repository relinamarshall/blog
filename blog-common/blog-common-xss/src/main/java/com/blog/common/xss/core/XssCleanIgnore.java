package com.blog.common.xss.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * XssCleanIgnore
 * <p>
 * 忽略 xss
 *
 * @author Wenzhou
 * @since 2023/5/12 9:45
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XssCleanIgnore {

    /**
     * 需要跳过的字段列表
     */
    String[] value() default {};
}
