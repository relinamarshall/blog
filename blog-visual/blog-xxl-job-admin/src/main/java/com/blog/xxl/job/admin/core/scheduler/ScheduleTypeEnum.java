package com.blog.xxl.job.admin.core.scheduler;


import com.blog.xxl.job.admin.core.util.I18nUtil;

/**
 * ScheduleTypeEnum
 *
 * @author Wenzhou
 * @since 2023/5/10 21:48
 */
public enum ScheduleTypeEnum {
    NONE(I18nUtil.getString("schedule_type_none")),

    /**
     * schedule by cron
     */
    CRON(I18nUtil.getString("schedule_type_cron")),

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE(I18nUtil.getString("schedule_type_fix_rate")),

    /**
     * schedule by fix delay (in seconds)， after the last time
     */
    /* FIX_DELAY(I18nUtil.getString("schedule_type_fix_delay")) */;

    private String title;

    ScheduleTypeEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static ScheduleTypeEnum match(String name, ScheduleTypeEnum defaultItem) {
        for (ScheduleTypeEnum item : ScheduleTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return defaultItem;
    }

}
