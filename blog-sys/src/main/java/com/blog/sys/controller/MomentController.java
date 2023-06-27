package com.blog.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Moment;
import com.blog.sys.service.MomentService;

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

import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * MomentController
 *
 * @author Wenzhou
 * @since 2023/6/23 17:28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/moment")
@Tag(name = "博客系统动态管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MomentController {
    /**
     * momentService
     */
    private final MomentService momentService;

    /**
     * 分页查询动态信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<Moment>> getMomentPage(Page<Moment> page, Moment moment) {
        return R.ok(momentService.page(page, Wrappers.<Moment>lambdaQuery()
                .like(CharSequenceUtil.isNotBlank(moment.getContent()), Moment::getContent, moment.getContent())
                .orderByDesc(Moment::getCrtTm)));
    }

    /**
     * 查询动态列表
     *
     * @return R
     */
    @GetMapping("/list")
    public R<List<Moment>> getMomentList() {
        return R.ok(momentService.getMomentListByUserId());
    }

    /**
     * 查询单动态列表
     *
     * @return R
     */
    @GetMapping("/{id}")
    public R<Moment> getMomentList(@PathVariable("id") String id) {
        return R.ok(momentService.getById(id));
    }

    /**
     * 添加动态
     *
     * @param moment Moment
     * @return R
     */
    @Log("添加动态")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('blog_moment_add')")
    public R<Void> save(@Valid @RequestBody Moment moment) {
        moment.setUserId(SecurityUtils.getUser().getId());
        return momentService.save(moment) ? R.ok() : R.failed();
    }

    /**
     * 修改动态
     *
     * @param moment 动态信息
     * @return success/false
     */
    @PutMapping
    @Log("修改动态")
    @PreAuthorize("@pms.hasPermission('blog_moment_edit')")
    public R<Void> updateById(@Valid @RequestBody Moment moment) {
        return momentService.updateById(moment) ? R.ok() : R.failed();
    }

    /**
     * 删除动态
     *
     * @param id String
     * @return R
     */
    @Log("删除动态")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('blog_moment_del')")
    public R<Void> removeById(@PathVariable String id) {
        return momentService.removeById(id) ? R.ok() : R.failed();
    }
}
