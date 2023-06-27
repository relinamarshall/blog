package com.blog.upms.core.util;


import com.blog.common.core.util.SpringContextHolder;
import com.blog.upms.core.feign.RemoteDictService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;

/**
 * ParamResolver
 * <p>
 * 系统参数配置解析器
 *
 * @author Wenzhou
 * @since 2023/5/8 16:07
 */
@UtilityClass
public class ParamResolver {

    /**
     * 根据key 查询value 配置
     *
     * @param key        key
     * @param defaultVal 默认值
     * @return value
     */
    public Long getLong(String key, Long... defaultVal) {
        return checkAndGet(key, Long.class, defaultVal);
    }

    /**
     * 根据key 查询value 配置
     *
     * @param key        key
     * @param defaultVal 默认值
     * @return value
     */
    public String getStr(String key, String... defaultVal) {
        return checkAndGet(key, String.class, defaultVal);
    }

    private <T> T checkAndGet(String key, Class<T> clazz, T... defaultVal) {
        // 校验入参是否合法
        if (CharSequenceUtil.isBlank(key) || defaultVal.length > 1) {
            throw new IllegalArgumentException("参数不合法");
        }

        RemoteDictService remoteDictService = SpringContextHolder.getBean(RemoteDictService.class);

        String result = remoteDictService.getBySysCfgKey(key).getData();

        if (CharSequenceUtil.isNotBlank(result)) {
            return Convert.convert(clazz, result);
        }

        if (defaultVal.length == 1) {
            return Convert.convert(clazz, defaultVal.clone()[0]);

        }
        return null;
    }

}

