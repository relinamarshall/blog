package com.blog.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.sys.entity.Tag;

import java.util.List;

/**
 * TagService
 *
 * @author Wenzhou
 * @since 2023/6/23 13:17
 */
public interface TagService extends IService<Tag> {
    /**
     * 获取当前用户的标签列表
     *
     * @return List<Tag>
     */
    List<Tag> getTagListByUserId();

    /**
     * 添加标签
     *
     * @param tag Tag
     * @return Boolean
     */
    Boolean saveTag(Tag tag);
}
