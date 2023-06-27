package com.blog.upms.core.feign;


import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.core.constant.ServiceNameConstants;
import com.blog.common.core.util.R;
import com.blog.upms.core.dto.UserInfo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RemoteUserService
 *
 * @author Wenzhou
 * @since 2023/5/8 16:00
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return R
     */
    @GetMapping(value = "/user/info/{username}", headers = SecurityConstants.HEADER_FROM_IN)
    R<UserInfo> info(@PathVariable("username") String username);
}

