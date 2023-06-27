package com.blog.upms.core.dto;

import com.blog.upms.core.entity.User;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserDto
 *
 * @author Wenzhou
 * @since 2023/5/12 10:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends User {
    /**
     * 角色ID
     */
    private List<String> role;

    /**
     * 新密码
     */
    private String newpassword1;

    /**
     * 验证码
     */
    private String code;
}