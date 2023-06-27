package com.blog.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.sys.entity.Friend;

import java.util.List;

/**
 * FriendService
 *
 * @author Wenzhou
 * @since 2023/6/23 16:55
 */
public interface FriendService extends IService<Friend> {
    /**
     * 获取用户的友链列表
     *
     * @return List<Friend>
     */
    List<Friend> getFriendListByUserId();

    /**
     * 添加友链
     *
     * @param friend Friend
     * @return Boolean
     */
    Boolean saveFriend(Friend friend);
}
