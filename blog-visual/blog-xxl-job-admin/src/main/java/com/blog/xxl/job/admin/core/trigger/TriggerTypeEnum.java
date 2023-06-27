package com.blog.xxl.job.admin.core.trigger;


import com.blog.xxl.job.admin.core.util.I18nUtil;

/**
 * TriggerTypeEnum
 *
 * @author Wenzhou
 * @since 2023/5/10 21:40
 */
public enum TriggerTypeEnum {
    MANUAL(I18nUtil.getString("jobconf_trigger_type_manual")),
    CRON(I18nUtil.getString("jobconf_trigger_type_cron")),
    RETRY(I18nUtil.getString("jobconf_trigger_type_retry")),
    PARENT(I18nUtil.getString("jobconf_trigger_type_parent")),
    API(I18nUtil.getString("jobconf_trigger_type_api")),
    MISFIRE(I18nUtil.getString("jobconf_trigger_type_misfire")),
    ;

    TriggerTypeEnum(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }

}
