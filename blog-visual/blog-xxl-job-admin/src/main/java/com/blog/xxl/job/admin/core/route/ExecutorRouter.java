package com.blog.xxl.job.admin.core.route;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ExecutorRouter
 *
 * @author Wenzhou
 * @since 2023/5/10 22:11
 */
public abstract class ExecutorRouter {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ExecutorRouter.class);

    /**
     * route address
     *
     * @param triggerParam triggerParam
     * @param addressList  List<String>
     * @return ReturnT.content=address
     */
    public abstract ReturnT<String> route(TriggerParam triggerParam, List<String> addressList);

}
