package com.blog.xxl.job.admin.core.thread;


import com.blog.xxl.job.admin.core.config.XxlJobAdminConfig;
import com.blog.xxl.job.admin.core.model.XxlJobInfo;
import com.blog.xxl.job.admin.core.model.XxlJobLog;
import com.blog.xxl.job.admin.core.trigger.TriggerTypeEnum;
import com.blog.xxl.job.admin.core.util.I18nUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JobFailMonitorHelper
 *
 * @author Wenzhou
 * @since 2023/5/10 21:39
 */
public class JobFailMonitorHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobFailMonitorHelper.class);

    private static final JobFailMonitorHelper INSTANCE = new JobFailMonitorHelper();

    public static JobFailMonitorHelper getInstance() {
        return INSTANCE;
    }

    // ---------------------- monitor ----------------------

    private Thread monitorThread;

    private volatile boolean toStop = false;

    public void start() {
        monitorThread = new Thread(() -> {
            // monitor
            while (!toStop) {
                try {
                    List<Long> failLogIds = XxlJobAdminConfig.getAdminConfig()
                            .getXxlJobLogDao()
                            .findFailJobLogIds(1000);
                    if (failLogIds != null && !failLogIds.isEmpty()) {
                        for (long failLogId : failLogIds) {

                            // lock log
                            int lockRet = XxlJobAdminConfig.getAdminConfig()
                                    .getXxlJobLogDao()
                                    .updateAlarmStatus(failLogId, 0, -1);
                            if (lockRet < 1) {
                                continue;
                            }
                            XxlJobLog log = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().load(failLogId);
                            XxlJobInfo info = XxlJobAdminConfig.getAdminConfig()
                                    .getXxlJobInfoDao()
                                    .loadById(log.getJobId());

                            // 1、fail retry monitor
                            if (log.getExecutorFailRetryCount() > 0) {
                                JobTriggerPoolHelper.trigger(log.getJobId(), TriggerTypeEnum.RETRY,
                                        (log.getExecutorFailRetryCount() - 1), log.getExecutorShardingParam(),
                                        log.getExecutorParam(), null);
                                String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>"
                                        + I18nUtil.getString("jobconf_trigger_type_retry")
                                        + "<<<<<<<<<<< </span><br>";
                                log.setTriggerMsg(log.getTriggerMsg() + retryMsg);
                                XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateTriggerInfo(log);
                            }

                            // 2、fail alarm monitor
                            int newAlarmStatus = 0; // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
                            if (info != null) {
                                boolean alarmResult = XxlJobAdminConfig.getAdminConfig()
                                        .getJobAlarmer()
                                        .alarm(info, log);
                                newAlarmStatus = alarmResult ? 2 : 3;
                            } else {
                                newAlarmStatus = 1;
                            }

                            XxlJobAdminConfig.getAdminConfig()
                                    .getXxlJobLogDao()
                                    .updateAlarmStatus(failLogId, -1, newAlarmStatus);
                        }
                    }

                } catch (Exception e) {
                    if (!toStop) {
                        LOGGER.error(">>>>>>>>>>> xxl-job, job fail monitor thread error:{}", e);
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception e) {
                    if (!toStop) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }

            }

            LOGGER.info(">>>>>>>>>>> xxl-job, job fail monitor thread stop");

        });
        monitorThread.setDaemon(true);
        monitorThread.setName("xxl-job, admin JobFailMonitorHelper");
        monitorThread.start();
    }

    public void toStop() {
        toStop = true;
        // interrupt and wait
        monitorThread.interrupt();
        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
