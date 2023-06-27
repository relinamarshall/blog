package com.blog.xxl.job.admin.core.alarm;


import com.blog.xxl.job.admin.core.model.XxlJobInfo;
import com.blog.xxl.job.admin.core.model.XxlJobLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JobAlarmer
 *
 * @author Wenzhou
 * @since 2023/5/10 21:26
 */
@Component
public class JobAlarmer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobAlarmer.class);

    private ApplicationContext applicationContext;

    private List<JobAlarm> jobAlarmList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, JobAlarm> serviceBeanMap = applicationContext.getBeansOfType(JobAlarm.class);
        if (serviceBeanMap.size() > 0) {
            jobAlarmList = new ArrayList<>(serviceBeanMap.values());
        }
    }

    /**
     * job alarm
     *
     * @param info   XxlJobInfo
     * @param jobLog XxlJobInfo
     * @return boolean
     */
    public boolean alarm(XxlJobInfo info, XxlJobLog jobLog) {
        boolean result = false;
        if (jobAlarmList != null && jobAlarmList.size() > 0) {
            // success means all-success
            result = true;
            for (JobAlarm alarm : jobAlarmList) {
                boolean resultItem = false;
                try {
                    resultItem = alarm.doAlarm(info, jobLog);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                if (!resultItem) {
                    result = false;
                }
            }
        }
        return result;
    }

}
