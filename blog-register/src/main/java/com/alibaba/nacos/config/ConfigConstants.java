package com.alibaba.nacos.config;

/**
 * ConfigConstants
 * <p>
 * 覆盖nacos 默认配置
 *
 * @author Wenzhou
 * @since 2023/5/10 13:47
 */
public final class ConfigConstants {
    private ConfigConstants() {

    }
    /**
     * The System property name of Standalone mode
     */
    public static final String STANDALONE_MODE = "nacos.standalone";

    /**
     * 是否开启认证
     */
    public static final String AUTH_ENABLED = "nacos.core.auth.enabled";

    /**
     * 日志目录
     */
    public static final String LOG_BASEDIR = "server.tomcat.basedir";

    /**
     * access_log日志开关
     */
    public static final String LOG_ENABLED = "server.tomcat.accesslog.enabled";
}
