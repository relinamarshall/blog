package com.blog.common.core.constant;

/**
 * SecurityConstants
 *
 * @author Wenzhou
 * @since 2023/6/19 16:09
 */
public final class SecurityConstants {
    private SecurityConstants() {

    }

    /**
     * 角色前缀
     */
    public static final String ROLE = "ROLE_";

    /**
     * 项目的license
     */
    public static final String PROJECT_LICENSE = "https://blog_cloud.com";

    /**
     * 内部
     */
    public static final String FROM_IN = "Y";

    /**
     * 标志
     */
    public static final String FROM = "from";

    /**
     * 请求header
     */
    public static final String HEADER_FROM_IN = FROM + "=" + FROM_IN;

    /**
     * 默认登录URL
     */
    public static final String OAUTH_TOKEN_URL = "/oauth2/token";

    /**
     * grant_type
     */
    public static final String REFRESH_TOKEN = "refresh_token";

    /**
     * 手机号登录
     */
    public static final String APP = "app";

    /**
     * {bcrypt} 加密的特征码
     */
    public static final String BCRYPT = "{bcrypt}";

    /**
     * {noop} 加密的特征码
     */
    public static final String NOOP = "{noop}";

    /**
     * 资源服务器默认bean名称
     */
    public static final String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * 用户信息
     */
    public static final String DETAILS_USER = "user_info";

    /**
     * 协议字段
     */
    public static final String DETAILS_LICENSE = "license";

    /**
     * 验证码有效期,默认 60秒
     */
    public static final long CODE_TIME = 60;

    /**
     * 验证码长度
     */
    public static final String CODE_SIZE = "6";

    /**
     * 客户端模式
     */
    public static final String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 客户端ID
     */
    public static final String CLIENT_ID = "clientId";

    /**
     * 短信登录 参数名称
     */
    public static final String SMS_PARAMETER_NAME = "phone";

    /**
     * 授权码模式confirm
     */
    public static final String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";
}
