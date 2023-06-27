package com.blog.upms.biz.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.common.core.constant.CommonConstants;
import com.blog.common.core.constant.enums.MenuTypeEnum;
import com.blog.common.core.exception.ErrorCodes;
import com.blog.common.core.util.MsgUtil;
import com.blog.common.core.util.R;
import com.blog.upms.biz.mapper.RoleMapper;
import com.blog.upms.biz.mapper.UserMapper;
import com.blog.upms.biz.mapper.UserRoleMapper;
import com.blog.upms.biz.service.MenuService;
import com.blog.upms.biz.service.UserService;
import com.blog.upms.core.dto.UserDto;
import com.blog.upms.core.dto.UserInfo;
import com.blog.upms.core.entity.Role;
import com.blog.upms.core.entity.User;
import com.blog.upms.core.entity.UserRole;
import com.blog.upms.core.util.ParamResolver;
import com.blog.upms.core.vo.UserVo;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * UserServiceImpl
 *
 * @author Wenzhou
 * @since 2023/5/12 12:48
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private final RoleMapper roleMapper;

    private final MenuService menuService;

    private final UserRoleMapper userRoleMapper;

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserDto userDto) {
        User sysUser = new User();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        userDto.getRole().stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(roleId);
            return userRole;
        }).forEach(userRoleMapper::insert);
        return Boolean.TRUE;
    }

    /**
     * 通过查用户的全部信息
     *
     * @param sysUser 用户
     * @return UserInfo
     */
    @Override
    public UserInfo getUserInfo(User sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(sysUser);
        // 设置角色列表
        List<Role> roleList = roleMapper.listRolesByUserId(sysUser.getId());
        userInfo.setRoleList(roleList);
        // 设置角色列表 （ID）
        List<String> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roleIds, String.class));

        // 设置权限列表（menu.permission）
        Set<String> permissions = roleIds.stream()
                .map(menuService::findMenuByRoleId)
                .flatMap(Collection::stream)
                .filter(m -> MenuTypeEnum.BUTTON.getType().equals(m.getType()))
                .map(com.blog.upms.core.entity.Menu::getPermission)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));

        return userInfo;
    }

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return IPage<UserVo>
     */
    @Override
    public IPage<UserVo> getUserWithRolePage(Page<UserVo> page, UserDto userDTO) {
        return baseMapper.getUserVosPage(page, userDTO);
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVo getUserVoById(String id) {
        return baseMapper.getUserVoById(id);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
    public Boolean removeUserById(User sysUser) {
        Boolean isSuccess = userRoleMapper.deleteByUserId(sysUser.getId());
        Assert.isTrue(isSuccess, "删除用户角色失败");
        return this.removeById(sysUser);
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
    public R<Boolean> updateUserInfo(UserDto userDto) {
        UserVo vo = baseMapper.getUserVoByUsername(userDto.getUsername());

        // 修改密码逻辑
        User sysUser = new User();
        if (StrUtil.isNotBlank(userDto.getNewpassword1())) {
            Assert.isTrue(ENCODER.matches(userDto.getPassword(), vo.getPassword()),
                    MsgUtil.getMessage(ErrorCodes.SYS_USER_UPDATE_PASSWORDERROR));
            sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        }
        sysUser.setId(vo.getId());
        sysUser.setAvatar(userDto.getAvatar());
        return R.ok(this.updateById(sysUser));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
    public R<Boolean> updateUser(UserDto userDto) {
        User sysUser = new User();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdTm(LocalDateTime.now());

        if (CharSequenceUtil.isNotBlank(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        userRoleMapper.delete(Wrappers.<UserRole>update().lambda().eq(UserRole::getUserId, userDto.getId()));
        userDto.getRole().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return R.ok();
    }

    /**
     * 注册用户 赋予用户默认角色
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @Override
    public R<Boolean> registerUser(UserDto userDto) {
        // 判断用户名是否存在
        User sysUser = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userDto.getUsername()));
        if (sysUser != null) {
            return R.failed(MsgUtil.getMessage(ErrorCodes.SYS_USER_USERNAME_EXISTING, userDto.getUsername()));
        }

        // 获取默认角色编码
        String defaultRole = ParamResolver.getStr("USER_DEFAULT_ROLE");
        // 默认角色
        Role sysRole = roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getCode, defaultRole));

        if (sysRole == null) {
            return R.failed(MsgUtil.getMessage(ErrorCodes.SYS_PARAM_CONFIG_ERROR, "USER_DEFAULT_ROLE"));
        }

        userDto.setRole(Collections.singletonList(sysRole.getId()));
        return R.ok(saveUser(userDto));
    }

}
