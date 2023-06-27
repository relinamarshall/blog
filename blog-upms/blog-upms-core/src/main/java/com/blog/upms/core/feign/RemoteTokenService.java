package com.blog.upms.core.feign;


import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.core.constant.ServiceNameConstants;
import com.blog.common.core.util.R;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * RemoteTokenService
 *
 * @author Wenzhou
 * @since 2023/5/8 16:00
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.AUTH_SERVICE)
public interface RemoteTokenService {
    /**
     * 分页查询token 信息
     *
     * @param params 分页参数
     * @return page
     */
    @PostMapping(value = "/token/page", headers = SecurityConstants.HEADER_FROM_IN)
    R getTokenPage(@RequestBody Map<String, Object> params);

    /**
     * 删除token
     *
     * @param token token
     * @return
     */
    @DeleteMapping(value = "/token/{token}", headers = SecurityConstants.HEADER_FROM_IN)
    R<Boolean> removeToken(@PathVariable("token") String token);
}
