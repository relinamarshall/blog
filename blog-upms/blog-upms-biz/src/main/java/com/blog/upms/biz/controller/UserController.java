package com.blog.upms.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.exception.ErrorCodes;
import com.blog.common.core.util.MsgUtil;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.common.security.annotation.Inner;
import com.blog.common.security.util.SecurityUtils;
import com.blog.common.xss.core.XssCleanIgnore;
import com.blog.upms.biz.service.UserService;
import com.blog.upms.core.dto.UserDto;
import com.blog.upms.core.dto.UserInfo;
import com.blog.upms.core.entity.User;
import com.blog.upms.core.vo.UserInfoVo;
import com.blog.upms.core.vo.UserVo;

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

import cn.hutool.core.collection.CollUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * UserController
 * <p>
 * 用户管理模块
 *
 * @author Wenzhou
 * @since 2023/5/12 11:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {

    private final UserService userService;

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/info"})
    public R<UserInfoVo> info() {
        String username = SecurityUtils.getUser().getUsername();
        User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));
        if (user == null) {
            return R.failed(MsgUtil.getMessage(ErrorCodes.SYS_USER_QUERY_ERROR));
        }
        UserInfo userInfo = userService.getUserInfo(user);
        UserInfoVo vo = new UserInfoVo();
        vo.setUser(userInfo.getUser());
        vo.setRoles(userInfo.getRoles());
        vo.setPermissions(userInfo.getPermissions());
        return R.ok(vo);
    }

    /**
     * 获取指定用户全部信息
     *
     * @return 用户信息
     */
    @Inner
    @GetMapping("/info/{username}")
    public R<UserInfo> info(@PathVariable String username) {
        User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));
        if (user == null) {
            return R.failed(MsgUtil.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, username));
        }
        return R.ok(userService.getUserInfo(user));
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public R<UserVo> user(@PathVariable String id) {
        return R.ok(userService.getUserVoById(id));
    }

    /**
     * 判断用户是否存在
     *
     * @param userDto 查询条件
     * @return Boolean
     */
    @Inner(false)
    @GetMapping("/check/exist")
    public R<Boolean> isExist(UserDto userDto) {
        List<User> sysUserList = userService.list(new QueryWrapper<>(userDto));
        if (CollUtil.isNotEmpty(sysUserList)) {
            return R.ok(Boolean.TRUE, MsgUtil.getMessage(ErrorCodes.SYS_USER_EXISTING));
        }
        return R.ok(Boolean.FALSE);
    }

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @Log("删除用户信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_user_del')")
    public R<Boolean> userDel(@PathVariable("id") String id) {
        User sysUser = userService.getById(id);
        return R.ok(userService.removeUserById(sysUser));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @Log("添加用户")
    @PostMapping
    @XssCleanIgnore({"password"})
    @PreAuthorize("@pms.hasPermission('sys_user_add')")
    public R<Boolean> user(@RequestBody UserDto userDto) {
        return R.ok(userService.saveUser(userDto));
    }

    /**
     * 管理员更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @Log("更新用户信息")
    @PutMapping
    @XssCleanIgnore({"password"})
    @PreAuthorize("@pms.hasPermission('sys_user_edit')")
    public R<Boolean> updateUser(@Valid @RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    /**
     * 分页查询用户
     *
     * @param page    参数集
     * @param userDto 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R<IPage<UserVo>> getUserPage(Page<UserVo> page, UserDto userDto) {
        return R.ok(userService.getUserWithRolePage(page, userDto));
    }

    /**
     * 个人修改个人信息
     *
     * @param userDto userDto
     * @return success/false
     */
    @Log("修改个人信息")
    @PutMapping("/edit")
    @XssCleanIgnore({"password", "newpassword1"})
    public R<Boolean> updateUserInfo(@Valid @RequestBody UserDto userDto) {
        userDto.setUsername(SecurityUtils.getUser().getUsername());
        return userService.updateUserInfo(userDto);
    }
}
