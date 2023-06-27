package com.blog.upms.biz.controller;


import com.blog.common.core.util.R;
import com.blog.upms.core.feign.RemoteTokenService;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * TokenController
 * <p>
 * getTokenPage 管理
 *
 * @author Wenzhou
 * @since 2023/5/12 11:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Tag(name = "令牌管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class TokenController {

    private final RemoteTokenService remoteTokenService;

    /**
     * 分页token 信息
     *
     * @param params 参数集
     * @return token集合
     */
    @GetMapping("/page")
    public R token(@RequestParam Map<String, Object> params) {
        return remoteTokenService.getTokenPage(params);
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_token_del')")
    public R<Boolean> delete(@PathVariable String id) {
        return remoteTokenService.removeToken(id);
    }

}

