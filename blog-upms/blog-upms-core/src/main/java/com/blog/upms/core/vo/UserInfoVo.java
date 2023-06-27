package com.blog.upms.core.vo;

import com.blog.upms.core.entity.User;

import java.io.Serializable;

import lombok.Data;

/**
 * UserInfoVo
 *
 * @author Wenzhou
 * @since 2023/5/12 11:27
 */
@Data
public class UserInfoVo implements Serializable {

    /**
     * 用户基本信息
     */
    private User user;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;
}

