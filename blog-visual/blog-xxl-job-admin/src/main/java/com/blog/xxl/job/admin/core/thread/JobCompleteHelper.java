package com.blog.xxl.job.admin.core.thread;

import com.blog.xxl.job.admin.core.completer.XxlJobCompleter;
import com.blog.xxl.job.admin.core.config.XxlJobAdminConfig;
import com.blog.xxl.job.admin.core.model.XxlJobLog;
import com.blog.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * JobCompleteHelper
 *
 * @author Wenzhou
 * @since 2023/5/10 21:48
 */
public class JobCompleteHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompleteHelper.class);

    private static final JobCompleteHelper INSTANCE = new JobCompleteHelper();

    public static JobCompleteHelper getInstance() {
        return INSTANCE;
    }

    // ---------------------- monitor ----------------------

    private ThreadPoolExecutor callbackThreadPool = null;

    private Thread monitorThread;

    private volatile boolean toStop = false;

    public void start() {
        // for callback
        callbackThreadPool = new ThreadPoolExecutor(2, 20, 30L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(3000), r ->
                new Thread(r, "xxl-job, admin JobLosedMonitorHelper-callbackThreadPool-" + r.hashCode()),
                (r, executor) -> {
                    r.run();
                    LOGGER.warn(
                            ">>>>>>>>>>> xxl-job, callback too fast, match threadpool rejected handler(run now).");
                });

        // for monitor
        monitorThread = new Thread(() -> {
            // wait for JobTriggerPoolHelper-init
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                if (!toStop) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            // monitor
            while (!toStop) {
                try {
                    // 任务结果丢失处理：调度记录停留在 "运行中" 状态超过10min，且对应执行器心跳注册失败不在线，则将本地调度主动标记失败；
                    Date losedTime = DateUtil.addMinutes(new Date(), -10);
                    List<Long> losedJobIds = XxlJobAdminConfig.getAdminConfig()
                            .getXxlJobLogDao()
                            .findLostJobIds(losedTime);

                    if (losedJobIds != null && losedJobIds.size() > 0) {
                        for (Long logId : losedJobIds) {

                            XxlJobLog jobLog = new XxlJobLog();
                            jobLog.setId(logId);

                            jobLog.setHandleTime(new Date());
                            jobLog.setHandleCode(ReturnT.FAIL_CODE);
                            jobLog.setHandleMsg(I18nUtil.getString("joblog_lost_fail"));

                            XxlJobCompleter.updateHandleInfoAndFinish(jobLog);
                        }
                    }
                } catch (Exception e) {
                    if (!toStop) {
                        LOGGER.error(">>>>>>>>>>> xxl-job, job fail monitor thread error:{}", e);
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (Exception e) {
                    if (!toStop) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
            LOGGER.info(">>>>>>>>>>> xxl-job, JobLosedMonitorHelper stop");
        });
        monitorThread.setDaemon(true);
        monitorThread.setName("xxl-job, admin JobLosedMonitorHelper");
        monitorThread.start();
    }

    public void toStop() {
        toStop = true;

        // stop registryOrRemoveThreadPool
        callbackThreadPool.shutdownNow();

        // stop monitorThread (interrupt and wait)
        monitorThread.interrupt();
        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    // ---------------------- helper ----------------------

    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        callbackThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (HandleCallbackParam handleCallbackParam : callbackParamList) {
                    ReturnT<String> callbackResult = callback(handleCallbackParam);
                    LOGGER.debug(">>>>>>>>> JobApiController.callback {}, handleCallbackParam={}, callbackResult={}",
                            (callbackResult.getCode() == ReturnT.SUCCESS_CODE ? "success" : "fail"),
                            handleCallbackParam, callbackResult);
                }
            }
        });

        return ReturnT.SUCCESS;
    }

    private ReturnT<String> callback(HandleCallbackParam handleCallbackParam) {
        // valid log item
        XxlJobLog log = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().load(handleCallbackParam.getLogId());
        if (log == null) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "log item not found.");
        }
        if (log.getHandleCode() > 0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "log repeate callback.");
            // avoid
            // repeat
            // callback,
            // trigger
            // child
            // job
            // etc
        }

        // handle msg
        StringBuffer handleMsg = new StringBuffer();
        if (log.getHandleMsg() != null) {
            handleMsg.append(log.getHandleMsg()).append("<br>");
        }
        if (handleCallbackParam.getHandleMsg() != null) {
            handleMsg.append(handleCallbackParam.getHandleMsg());
        }

        // success, save log
        log.setHandleTime(new Date());
        log.setHandleCode(handleCallbackParam.getHandleCode());
        log.setHandleMsg(handleMsg.toString());
        XxlJobCompleter.updateHandleInfoAndFinish(log);

        return ReturnT.SUCCESS;
    }

}
