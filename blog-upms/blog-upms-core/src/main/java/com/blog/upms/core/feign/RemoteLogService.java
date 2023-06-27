package com.blog.upms.core.feign;

import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.core.constant.ServiceNameConstants;
import com.blog.common.core.util.R;
import com.blog.upms.core.entity.Log;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * RemoteLogService
 *
 * @author Wenzhou
 * @since 2023/6/19 17:55
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteLogService {
    /**
     * 保存日志
     *
     * @param log 日志实体
     * @return success or false
     */
    @PostMapping(value = "/log", headers = SecurityConstants.HEADER_FROM_IN)
    R<Boolean> saveLog(@RequestBody Log log);
}