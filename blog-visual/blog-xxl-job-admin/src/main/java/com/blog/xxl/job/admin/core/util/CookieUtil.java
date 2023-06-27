package com.blog.xxl.job.admin.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.experimental.UtilityClass;

/**
 * CookieUtil
 *
 * @author Wenzhou
 * @since 2023/5/10 21:18
 */
@UtilityClass
public class CookieUtil {
    /**
     * COOKIE_MAX_AGE
     * <p>
     * 默认缓存时间,单位/秒, 2H
     */
    private static final int COOKIE_MAX_AGE = Integer.MAX_VALUE;

    /**
     * COOKIE_PATH
     * <p>
     * 保存路径,根路径
     */
    private static final String COOKIE_PATH = "/";

    /**
     * 保存
     *
     * @param response   HttpServletResponse
     * @param key        String
     * @param value      String
     * @param ifRemember boolean
     */
    public static void set(HttpServletResponse response, String key, String value, boolean ifRemember) {
        int age = ifRemember ? COOKIE_MAX_AGE : -1;
        set(response, key, value, null, COOKIE_PATH, age, true);
    }

    /**
     * 保存
     *
     * @param response HttpServletResponse
     * @param key      String
     * @param value    String
     * @param maxAge   String
     */
    private static void set(HttpServletResponse response, String key, String value, String domain, String path,
                            int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        response.addCookie(cookie);
    }

    /**
     * 查询value
     *
     * @param request HttpServletRequest
     * @param key     String
     * @return String
     */
    public static String getValue(HttpServletRequest request, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 查询Cookie
     *
     * @param request HttpServletRequest
     * @param key     String
     */
    private static Cookie get(HttpServletRequest request, String key) {
        Cookie[] arrCookie = request.getCookies();
        if (arrCookie != null && arrCookie.length > 0) {
            for (Cookie cookie : arrCookie) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 删除Cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param key      String
     */
    public static void remove(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            set(response, key, "", null, COOKIE_PATH, 0, true);
        }
    }
}
