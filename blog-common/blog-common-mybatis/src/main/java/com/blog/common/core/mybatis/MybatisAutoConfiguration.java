package com.blog.common.core.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect;
import com.blog.common.core.mybatis.config.MybatisPlusMetaObjectHandler;
import com.blog.common.core.mybatis.plugins.DiyPaginationInnerInterceptor;
import com.blog.common.core.mybatis.resolver.SqlFilterArgumentResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MybatisAutoConfiguration
 * <p>
 * mybatis plus 统一配置
 *
 * @author Wenzhou
 * @since 2023/6/19 14:56
 */
@Configuration(proxyBeanMethods = false)
public class MybatisAutoConfiguration implements WebMvcConfigurer {
    /**
     * SQL过滤器避免SQL注入
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SqlFilterArgumentResolver());
    }

    /**
     * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DiyPaginationInnerInterceptor diy = new DiyPaginationInnerInterceptor();
        diy.setDbType(DbType.MYSQL);
        diy.setDialect(new MySqlDialect());
        interceptor.addInnerInterceptor(diy);
        return interceptor;
    }

    /**
     * 审计字段自动填充
     *
     * @return {@link MetaObjectHandler}
     */
    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }
}
