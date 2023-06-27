package com.blog.xxl.job.admin.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PermissionLimit
 * <p>
 * 权限限制
 *
 * @author Wenzhou
 * @since 2023/5/11 8:54
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionLimit {
    /**
     * 登录拦截 (默认拦截)
     */
    boolean limit() default true;

    /**
     * 要求管理员权限
     */
    boolean adminuser() default false;
}
