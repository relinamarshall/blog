package com.blog.upms.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.common.security.annotation.Inner;
import com.blog.upms.biz.service.OauthClientDetailsService;
import com.blog.upms.core.entity.OauthClientDetails;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


/**
 * OauthClientDetailsController
 * <p>
 * 客户端管理模块
 *
 * @author Wenzhou
 * @since 2023/5/12 11:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Tag(name = "客户端管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OauthClientDetailsController {

    private final OauthClientDetailsService oauthClientDetailsService;

    /**
     * 通过ID查询
     *
     * @param clientId 客户端id
     * @return OauthClientDetails
     */
    @GetMapping("/{clientId}")
    public R<List<OauthClientDetails>> getByClientId(@PathVariable String clientId) {
        return R.ok(oauthClientDetailsService
                .list(Wrappers.<OauthClientDetails>lambdaQuery().eq(OauthClientDetails::getClientId, clientId)));
    }

    /**
     * 简单分页查询
     *
     * @param page                  分页对象
     * @param sysOauthClientDetails 系统终端
     * @return
     */
    @GetMapping("/page")
    public R<IPage<OauthClientDetails>> getOauthClientDetailsPage(Page page,
                                                                     OauthClientDetails sysOauthClientDetails) {
        return R.ok(oauthClientDetailsService.page(page, Wrappers.query(sysOauthClientDetails)));
    }

    /**
     * 添加
     *
     * @param sysOauthClientDetails 实体
     * @return success/false
     */
    @Log("添加终端")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_client_add')")
    public R<Boolean> add(@Valid @RequestBody OauthClientDetails sysOauthClientDetails) {
        return R.ok(oauthClientDetailsService.save(sysOauthClientDetails));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @Log("删除终端")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_client_del')")
    public R<Boolean> removeById(@PathVariable String id) {
        return R.ok(oauthClientDetailsService.removeClientDetailsById(id));
    }

    /**
     * 编辑
     *
     * @param sysOauthClientDetails 实体
     * @return success/false
     */
    @Log("编辑终端")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_client_edit')")
    public R<Boolean> update(@Valid @RequestBody OauthClientDetails sysOauthClientDetails) {
        return R.ok(oauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails));
    }

    @Log("清除终端缓存")
    @DeleteMapping("/cache")
    @PreAuthorize("@pms.hasPermission('sys_client_del')")
    public R clearClientCache() {
        oauthClientDetailsService.clearClientCache();
        return R.ok();
    }

    @Inner
    @GetMapping("/getClientDetailsById/{clientId}")
    public R getClientDetailsById(@PathVariable String clientId) {
        return R.ok(oauthClientDetailsService.getOne(
                Wrappers.<OauthClientDetails>lambdaQuery().eq(OauthClientDetails::getClientId, clientId), false));
    }
}
