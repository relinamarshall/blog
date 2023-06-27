package com.blog.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.util.AssertUtil;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Friend;
import com.blog.sys.mapper.FriendMapper;
import com.blog.sys.service.FriendService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * FriendServiceImpl
 *
 * @author Wenzhou
 * @since 2023/6/23 16:55
 */
@Service
@RequiredArgsConstructor
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    /**
     * friendMapper
     */
    private final FriendMapper friendMapper;

    @Override
    public List<Friend> getFriendListByUserId() {
        String userId = SecurityUtils.getUser().getId();
        return friendMapper.selectList(Wrappers.<Friend>lambdaQuery()
                .eq(StringUtils.isNotBlank(userId), Friend::getUserId, userId)
                .orderByDesc(Friend::getCrtTm));
    }

    @Override
    public Boolean saveFriend(Friend friend) {
        friend.setUserId(SecurityUtils.getUser().getId());
        Friend t = getOne(Wrappers.<Friend>lambdaQuery().eq(Friend::getName, friend.getName()));
        AssertUtil.isNull(t, "该友链名称已存在");
        return save(friend);
    }
}
