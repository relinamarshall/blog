package com.blog.common.security.service;


import com.blog.common.core.constant.CacheConstants;
import com.blog.common.core.util.R;
import com.blog.common.security.pojo.ExtUser;
import com.blog.upms.core.dto.UserInfo;
import com.blog.upms.core.feign.RemoteUserService;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wenzhou
 * @since 2023/5/9 16:19
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class ExtUserDetailsServiceImpl implements ExtUserDetailsService {

    private final RemoteUserService remoteUserService;

    private final CacheManager cacheManager;

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @return UserDetails
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (ExtUser) Objects.requireNonNull(cache.get(username)).get();
        }

        R<UserInfo> result = remoteUserService.info(username);
        UserDetails userDetails = getUserDetails(result);
        if (cache != null) {
            cache.put(username, userDetails);
        }
        return userDetails;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}

