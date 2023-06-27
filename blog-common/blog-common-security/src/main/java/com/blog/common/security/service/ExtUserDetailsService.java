package com.blog.common.security.service;

import com.blog.common.core.constant.CommonConstants;
import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.core.util.R;
import com.blog.common.core.util.RetOps;
import com.blog.common.security.pojo.ExtUser;
import com.blog.upms.core.dto.UserInfo;
import com.blog.upms.core.entity.User;

import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;

/**
 * ExtUserDetailsService
 *
 * @author Wenzhou
 * @since 2023/5/8 16:29
 */
public interface ExtUserDetailsService extends UserDetailsService, Ordered {

    /**
     * 是否支持此客户端校验
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值 默认取最大的
     *
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return UserDetails
     */
    default UserDetails getUserDetails(R<UserInfo> result) {
        UserInfo info = RetOps.of(result).getData().orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        Set<String> dbAuthsSet = new HashSet<>();

        if (ArrayUtil.isNotEmpty(info.getRoles())) {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));
        }

        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        User user = info.getUser();

        // 构造security用户
        return new ExtUser(user.getId(), user.getUsername(), SecurityConstants.BCRYPT + user.getPassword(),
                true, true, true,
                CharSequenceUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), authorities);
    }

    /**
     * 通过用户实体查询
     *
     * @param user user
     * @return UserDetails
     */
    default UserDetails loadUserByUser(ExtUser user) {
        return this.loadUserByUsername(user.getUsername());
    }
}
