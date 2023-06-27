package com.blog.upms.core.feign;

import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.core.constant.ServiceNameConstants;
import com.blog.common.core.util.R;
import com.blog.upms.core.entity.OauthClientDetails;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteClientDetailsService {
    /**
     * 通过clientId 查询客户端信息
     *
     * @param clientId 用户名
     * @return R
     */
    @GetMapping(value = "/client/getClientDetailsById/{clientId}", headers = SecurityConstants.HEADER_FROM_IN)
    R<OauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId);

    /**
     * 查询全部客户端
     *
     * @return R
     */
    @GetMapping(value = "/client/list", headers = SecurityConstants.HEADER_FROM_IN)
    R<List<OauthClientDetails>> listClientDetails();
}