package com.blog.upms.biz.xxljob;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * DemoJobHandler
 *
 * @author Wenzhou
 * @since 2023/5/23 15:36
 */
@Slf4j
@Component
public class DemoJobHandler {
    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        String param = XxlJobHelper.getJobParam();
        int shardIndex = XxlJobHelper.getShardIndex();
        log.info("{}-{}", param, shardIndex);
        XxlJobHelper.handleSuccess();
    }
}
