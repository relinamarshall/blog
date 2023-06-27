package com.blog.common.core.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Optional;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * MybatisPlusMetaObjectHandler
 * <p>
 * MybatisPlus 自动填充配置
 *
 * @author Wenzhou
 * @since 2023/6/19 15:00
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis plus start 【insert fill】");
        LocalDateTime now = LocalDateTime.now();

        fillValIfNullByName("crtTm", now, metaObject, false);
        fillValIfNullByName("updTm", now, metaObject, false);
        fillValIfNullByName("crtBy", getUserName(), metaObject, false);
        fillValIfNullByName("updBy", getUserName(), metaObject, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis plus start 【update fill】");

        fillValIfNullByName("updTm", LocalDateTime.now(), metaObject, true);
        fillValIfNullByName("updBy", getUserName(), metaObject, true);
    }

    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
     *
     * @param fieldName  属性名
     * @param fieldVal   属性值
     * @param metaObject MetaObject
     * @param isCover    是否覆盖原有值,避免更新操作手动入参
     */
    private static void fillValIfNullByName(String fieldName,
                                            Object fieldVal,
                                            MetaObject metaObject,
                                            boolean isCover) {
        // 1. 没有 set 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (CharSequenceUtil.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    /**
     * 获取 spring security 当前的用户名
     *
     * @return 当前用户名
     */
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent()) {
            return authentication.getName();
        }
        return null;
    }
}
