package com.blog.common.security.component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * SecurityMessageSourceConfiguration
 * <p>
 * 注入自定义错误处理,覆盖 org/springframework/security/messages 内置异常
 *
 * @author Wenzhou
 * @since 2023/5/11 17:12
 */
@ConditionalOnWebApplication(type = SERVLET)
public class SecurityMessageSourceConfiguration implements WebMvcConfigurer {

    @Bean
    public MessageSource securityMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:i18n/errors/messages");
        messageSource.setDefaultLocale(Locale.CHINA);
        return messageSource;
    }
}
