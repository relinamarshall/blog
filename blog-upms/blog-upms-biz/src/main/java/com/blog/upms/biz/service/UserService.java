package com.blog.upms.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.core.util.R;
import com.blog.upms.core.dto.UserDto;
import com.blog.upms.core.dto.UserInfo;
import com.blog.upms.core.entity.User;
import com.blog.upms.core.vo.UserVo;

/**
 * UserService
 * <p>
 * 用户表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:39
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户信息
     *
     * @param sysUser 用户
     * @return userInfo
     */
    UserInfo getUserInfo(User sysUser);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return IPage<UserVO>
     */
    IPage<UserVo> getUserWithRolePage(Page<UserVo> page, UserDto userDTO);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return boolean
     */
    Boolean removeUserById(User sysUser);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto 用户信息
     * @return Boolean 操作成功返回true,操作失败返回false
     */
    R<Boolean> updateUserInfo(UserDto userDto);

    /**
     * 更新指定用户信息
     *
     * @param userDto 用户信息
     * @return Boolean 操作成功返回true,操作失败返回false
     */
    R<Boolean> updateUser(UserDto userDto);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVo getUserVoById(String id);

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    Boolean saveUser(UserDto userDto);

    /**
     * 注册用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    R<Boolean> registerUser(UserDto userDto);
}