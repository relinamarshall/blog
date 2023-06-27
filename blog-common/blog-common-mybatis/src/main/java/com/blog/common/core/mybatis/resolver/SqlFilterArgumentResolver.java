package com.blog.common.core.mybatis.resolver;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * SqlFilterArgumentResolver
 * <p>
 * 解决Mybatis Plus Order By SQL注入问题
 *
 * @author Wenzhou
 * @since 2023/6/19 15:09
 */
@Slf4j
public class SqlFilterArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String[] KEYWORDS = {"master", "truncate", "insert", "select", "delete", "update", "declare",
            "alter", "drop", "sleep", "extractvalue", "concat"};

    /**
     * 判断Controller是否包含page 参数
     *
     * @param parameter 参数
     * @return 是否过滤
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Page.class);
    }

    /**
     * page 只支持查询 GET ,如需解析POST获取请求报文体处理
     *
     * @param parameter     入参集合
     * @param mavContainer  model 和 view
     * @param webRequest    web相关
     * @param binderFactory 入参解析
     * @return 检查后新的page对象
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String[] ascs = null;
        String[] descs = null;
        String current = null;
        String size = null;
        if (null != request) {
            ascs = request.getParameterValues("ascs");
            descs = request.getParameterValues("descs");
            current = request.getParameter("current");
            size = request.getParameter("size");
        }

        Page<?> page = new Page<>();
        if (CharSequenceUtil.isNotBlank(current)) {
            page.setCurrent(Long.parseLong(Objects.requireNonNull(current)));
        }

        if (CharSequenceUtil.isNotBlank(size)) {
            page.setSize(Long.parseLong(Objects.requireNonNull(size)));
        }

        List<OrderItem> orderItemList = new ArrayList<>();
        Optional.ofNullable(ascs).ifPresent(s -> orderItemList.addAll(Arrays.stream(s)
                .filter(sqlInjectPredicate())
                .map(OrderItem::asc).collect(Collectors.toList())));
        Optional.ofNullable(descs).ifPresent(s -> orderItemList.addAll(Arrays.stream(s)
                .filter(sqlInjectPredicate())
                .map(OrderItem::desc).collect(Collectors.toList())));
        page.addOrder(orderItemList);

        return page;
    }

    /**
     * 判断用户输入里面有没有关键字
     *
     * @return Predicate
     */
    private Predicate<String> sqlInjectPredicate() {
        return sql -> Arrays.stream(KEYWORDS).noneMatch(keyword -> CharSequenceUtil.containsIgnoreCase(sql, keyword));
    }
}
