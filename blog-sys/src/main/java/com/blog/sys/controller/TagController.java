package com.blog.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.sys.service.TagService;

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
 * TagController
 *
 * @author Wenzhou
 * @since 2023/6/23 13:16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
@Tag(name = "博客系统标签管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class TagController {
    /**
     * tagService
     */
    private final TagService tagService;

    /**
     * 分页查询标签信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<com.blog.sys.entity.Tag>> getTagPage(Page<com.blog.sys.entity.Tag> page,
                                                        com.blog.sys.entity.Tag tag) {
        return R.ok(tagService.page(page, Wrappers.<com.blog.sys.entity.Tag>lambdaQuery()
                .like(CharSequenceUtil.isNotBlank(tag.getName()), com.blog.sys.entity.Tag::getName, tag.getName())));
    }

    /**
     * 查询标签列表
     *
     * @return R
     */
    @GetMapping("/list")
    public R<List<com.blog.sys.entity.Tag>> getTagList() {
        return R.ok(tagService.getTagListByUserId());
    }

    /**
     * 查询单标签列表
     *
     * @return R
     */
    @GetMapping("/{id}")
    public R<com.blog.sys.entity.Tag> getTagList(@PathVariable("id") String id) {
        return R.ok(tagService.getById(id));
    }

    /**
     * 添加标签
     *
     * @param tag Tag
     * @return R
     */
    @Log("添加标签")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('blog_tag_add')")
    public R<Void> save(@Valid @RequestBody com.blog.sys.entity.Tag tag) {
        return tagService.saveTag(tag) ? R.ok() : R.failed();
    }

    /**
     * 修改标签
     *
     * @param tag 标签信息
     * @return success/false
     */
    @PutMapping
    @Log("修改标签")
    @PreAuthorize("@pms.hasPermission('blog_tag_edit')")
    public R<Void> updateById(@Valid @RequestBody com.blog.sys.entity.Tag tag) {
        return tagService.updateById(tag) ? R.ok() : R.failed();
    }

    /**
     * 删除标签
     *
     * @param id String
     * @return R
     */
    @Log("删除标签")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('blog_tag_del')")
    public R<Void> removeById(@PathVariable String id) {
        return tagService.removeById(id) ? R.ok() : R.failed();
    }
}
