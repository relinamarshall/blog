package com.blog.common.xss.core;


import com.blog.common.xss.xss.XssUtil;

import org.jsoup.Jsoup;

/**
 * XssCleaner
 * <p>
 * xss 清理器
 *
 * @author Wenzhou
 * @since 2023/5/12 9:36
 */
public interface XssCleaner {

    /**
     * 清理 html
     *
     * @param html html
     * @return 清理后的数据
     */
    default String clean(String html) {
        return clean(html, XssType.FORM);
    }

    /**
     * 清理 html
     *
     * @param html html
     * @param type XssType
     * @return 清理后的数据
     */
    String clean(String html, XssType type);

    /**
     * 判断输入是否安全
     *
     * @param html html
     * @return 是否安全
     */
    default boolean isValid(String html) {
        return Jsoup.isValid(html, XssUtil.WHITE_LIST);
    }

}