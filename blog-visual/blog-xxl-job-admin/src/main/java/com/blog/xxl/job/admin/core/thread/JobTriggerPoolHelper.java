package com.blog.xxl.job.admin.core.thread;


import com.blog.xxl.job.admin.core.config.XxlJobAdminConfig;
import com.blog.xxl.job.admin.core.trigger.TriggerTypeEnum;
import com.blog.xxl.job.admin.core.trigger.XxlJobTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JobTriggerPoolHelper
 *
 * @author Wenzhou
 * @since 2023/5/10 21:39
 */
public class JobTriggerPoolHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobTriggerPoolHelper.class);

    /* ---------------------- trigger pool ---------------------- */

    /**
     * fast/slow thread pool
     */
    private ThreadPoolExecutor fastTriggerPool = null;

    private ThreadPoolExecutor slowTriggerPool = null;

    public void start() {
        fastTriggerPool = new ThreadPoolExecutor(10,
                XxlJobAdminConfig.getAdminConfig().getTriggerPoolFastMax(), 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), r ->
                new Thread(r, "xxl-job, admin JobTriggerPoolHelper-fastTriggerPool-" + r.hashCode()));

        slowTriggerPool = new ThreadPoolExecutor(10,
                XxlJobAdminConfig.getAdminConfig().getTriggerPoolSlowMax(), 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000), r ->
                new Thread(r, "xxl-job, admin JobTriggerPoolHelper-slowTriggerPool-" + r.hashCode()));
    }

    /**
     * triggerPool.shutdown();
     */
    public void stop() {
        fastTriggerPool.shutdownNow();
        slowTriggerPool.shutdownNow();
        LOGGER.info(">>>>>>>>> xxl-job trigger thread pool shutdown success.");
    }

    /**
     * job timeout count
     * <p>
     * ms > min
     */
    private volatile long minTim = System.currentTimeMillis() / 60000;

    private final ConcurrentMap<Integer, AtomicInteger> jobTimeoutCountMap = new ConcurrentHashMap<>();

    /**
     * add trigger
     */
    public void addTrigger(final int jobId, final TriggerTypeEnum triggerType, final int failRetryCount,
                           final String executorShardingParam, final String executorParam, final String addressList) {
        // choose thread pool
        ThreadPoolExecutor triggerPool = fastTriggerPool;
        AtomicInteger jobTimeoutCount = jobTimeoutCountMap.get(jobId);
        if (jobTimeoutCount != null && jobTimeoutCount.get() > 10) { // job-timeout 10
            // times in 1 min
            triggerPool = slowTriggerPool;
        }

        // trigger
        triggerPool.execute(() -> {
            long start = System.currentTimeMillis();
            try {
                // do trigger
                XxlJobTrigger.trigger(jobId, triggerType, failRetryCount, executorShardingParam, executorParam,
                        addressList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                // check timeout-count-map
                long minTimNow = System.currentTimeMillis() / 60000;
                if (minTim != minTimNow) {
                    minTim = minTimNow;
                    jobTimeoutCountMap.clear();
                }

                // incr timeout-count-map
                long cost = System.currentTimeMillis() - start;
                if (cost > 500) { // ob-timeout threshold 500ms
                    AtomicInteger timeoutCount = jobTimeoutCountMap.putIfAbsent(jobId, new AtomicInteger(1));
                    if (timeoutCount != null) {
                        timeoutCount.incrementAndGet();
                    }
                }
            }
        });
    }

    /* ---------------------- helper ---------------------- */

    /**
     * HELPER
     */
    private static final JobTriggerPoolHelper HELPER = new JobTriggerPoolHelper();

    public static void toStart() {
        HELPER.start();
    }

    public static void toStop() {
        HELPER.stop();
    }

    /**
     * @param jobId                 int
     * @param triggerType           TriggerTypeEnum
     * @param failRetryCount        int   >=0: use this param <0: use param from job info config
     * @param executorShardingParam String
     * @param executorParam         String  null: use job param not null: cover job param
     */
    public static void trigger(int jobId,
                               TriggerTypeEnum triggerType,
                               int failRetryCount,
                               String executorShardingParam,
                               String executorParam, String addressList) {
        HELPER.addTrigger(jobId, triggerType, failRetryCount, executorShardingParam, executorParam, addressList);
    }
}
