package com.blog.common.core.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

import lombok.experimental.UtilityClass;

/**
 * MsgUtils
 * <p>
 * i18n 工具类
 *
 * @author Wenzhou
 * @since 2023/6/19 16:27
 */
@UtilityClass
public class MsgUtil {

    /**
     * 通过code 获取中文错误信息
     *
     * @param code String
     * @return String
     */
    public String getMessage(String code) {
        MessageSource messageSource = SpringContextHolder.getBean("messageSource");
        return messageSource.getMessage(code, null, Locale.CHINA);
    }

    /**
     * 通过code 和参数获取中文错误信息
     *
     * @param code    String
     * @param objects Object[]
     * @return String
     */
    public String getMessage(String code, Object... objects) {
        MessageSource messageSource = SpringContextHolder.getBean("messageSource");
        return messageSource.getMessage(code, objects, Locale.CHINA);
    }

    /**
     * security 通过code 和参数获取中文错误信息
     *
     * @param code    String
     * @param objects Object[]
     * @return String
     */
    public String getSecurityMessage(String code, Object... objects) {
        MessageSource messageSource = SpringContextHolder.getBean("securityMessageSource");
        return messageSource.getMessage(code, objects, Locale.CHINA);
    }

}
