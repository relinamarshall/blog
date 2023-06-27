package com.blog.common.security.util;


import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.security.pojo.ExtUser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;

/**
 * SecurityUtils
 * <p>
 * 安全工具类
 *
 * @author Wenzhou
 * @since 2023/5/10 16:01
 */
@UtilityClass
public class SecurityUtils {
    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public ExtUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof ExtUser) {
            return (ExtUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public ExtUser getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getUser(authentication);
    }

    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public List<String> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roleIds = new ArrayList<>();
        authorities.stream()
                .filter(granted -> CharSequenceUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
                .forEach(granted -> roleIds.add(CharSequenceUtil.removePrefix(granted.getAuthority(),
                        SecurityConstants.ROLE)));
        return roleIds;
    }
}
