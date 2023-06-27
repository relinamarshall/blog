package com.blog.common.core.constant;

/**
 * CacheConstants
 *
 * @author Wenzhou
 * @since 2023/6/19 16:06
 */
public final class CacheConstants {
    private CacheConstants() {

    }

    /**
     * oauth 缓存前缀
     */
    public static final String PROJECT_OAUTH_ACCESS = "token::access_token";

    /**
     * oauth 缓存令牌前缀
     */
    public static final String PROJECT_OAUTH_TOKEN = "blog_oauth:token:";

    /**
     * 验证码前缀
     */
    public static final String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";

    /**
     * 菜单信息缓存
     */
    public static final String MENU_DETAILS = "menu_details";

    /**
     * 用户信息缓存
     */
    public static final String USER_DETAILS = "user_details";

    /**
     * 字典信息缓存
     */
    public static final String DICT_DETAILS = "dict_details";

    /**
     * oauth 客户端信息
     */
    public static final String CLIENT_DETAILS_KEY = "client:details";

    /**
     * 系统参数缓存
     */
    public static final String PARAMS_DETAILS = "sys_cfg_details";
}
