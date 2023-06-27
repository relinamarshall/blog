package com.blog.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Friend;
import com.blog.sys.service.FriendService;

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
 * FriendController
 *
 * @author Wenzhou
 * @since 2023/6/23 16:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@Tag(name = "博客系统友链管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class FriendController {
    /**
     * friendService
     */
    private final FriendService friendService;

    /**
     * 分页查询友链信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<Friend>> getFriendPage(Page<Friend> page, Friend friend) {
        return R.ok(friendService.page(page, Wrappers.<Friend>lambdaQuery()
                .like(CharSequenceUtil.isNotBlank(friend.getName()), Friend::getName, friend.getName())
                .like(CharSequenceUtil.isNotBlank(friend.getWebsite()), Friend::getWebsite, friend.getWebsite())
                .like(CharSequenceUtil.isNotBlank(friend.getDes()), Friend::getDes, friend.getDes())
                .orderByDesc(Friend::getCrtTm)));
    }

    /**
     * 查询友链列表
     *
     * @return R
     */
    @GetMapping("/list")
    public R<List<Friend>> getFriendList() {
        return R.ok(friendService.getFriendListByUserId());
    }

    /**
     * 查询单友链列表
     *
     * @return R
     */
    @GetMapping("/{id}")
    public R<Friend> getFriendList(@PathVariable("id") String id) {
        return R.ok(friendService.getById(id));
    }

    /**
     * 添加友链
     *
     * @param friend Friend
     * @return R
     */
    @Log("添加友链")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('blog_friend_add')")
    public R<Void> save(@Valid @RequestBody Friend friend) {
        return friendService.saveFriend(friend) ? R.ok() : R.failed();
    }

    /**
     * 修改友链
     *
     * @param friend 友链信息
     * @return success/false
     */
    @PutMapping
    @Log("修改友链")
    @PreAuthorize("@pms.hasPermission('blog_friend_edit')")
    public R<Void> updateById(@Valid @RequestBody Friend friend) {
        return friendService.updateById(friend) ? R.ok() : R.failed();
    }

    /**
     * 删除友链
     *
     * @param id String
     * @return R
     */
    @Log("删除友链")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('blog_friend_del')")
    public R<Void> removeById(@PathVariable String id) {
        return friendService.removeById(id) ? R.ok() : R.failed();
    }
}
