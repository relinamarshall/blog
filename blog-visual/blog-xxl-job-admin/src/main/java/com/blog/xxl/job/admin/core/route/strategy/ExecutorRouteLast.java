package com.blog.xxl.job.admin.core.route.strategy;

import com.blog.xxl.job.admin.core.route.ExecutorRouter;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;

import java.util.List;

/**
 * ExecutorRouteLast
 *
 * @author Wenzhou
 * @since 2023/5/11 8:21
 */
public class ExecutorRouteLast extends ExecutorRouter {
    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<>(addressList.get(addressList.size() - 1));
    }
}
