package com.blog.common.security.exception;

/**
 * OAuth2ErrorCodesExpand
 * <p>
 * OAuth2 异常信息
 *
 * @author Wenzhou
 * @since 2023/5/10 16:03
 */
public final class OAuth2ErrorCodesExpand {
    /**
     * 用户名未找到
     */
    public static final String USERNAME_NOT_FOUND = "username_not_found";

    /**
     * 错误凭证
     */
    public static final String BAD_CREDENTIALS = "bad_credentials";

    /**
     * 用户被锁
     */
    public static final String USER_LOCKED = "user_locked";

    /**
     * 用户禁用
     */
    public static final String USER_DISABLE = "user_disable";

    /**
     * 用户过期
     */
    public static final String USER_EXPIRED = "user_expired";

    /**
     * 证书过期
     */
    public static final String CREDENTIALS_EXPIRED = "credentials_expired";

    /**
     * scope 为空异常
     */
    public static final String SCOPE_IS_EMPTY = "scope_is_empty";

    /**
     * 令牌不存在
     */
    public static final String TOKEN_MISSING = "token_missing";

    /**
     * 未知的登录异常
     */
    public static final String UN_KNOW_LOGIN_ERROR = "un_know_login_error";

    /**
     * 不合法的Token
     */
    public static final String INVALID_BEARER_TOKEN = "invalid_bearer_token";
}
