package com.blog.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.util.AssertUtil;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Tag;
import com.blog.sys.mapper.TagMapper;
import com.blog.sys.service.TagService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * TagServiceImpl
 *
 * @author Wenzhou
 * @since 2023/6/23 13:18
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    /**
     * tagMapper
     */
    private final TagMapper tagMapper;

    @Override
    public List<Tag> getTagListByUserId() {
        String userId = SecurityUtils.getUser().getId();
        return tagMapper.selectList(Wrappers.<Tag>lambdaQuery()
                .eq(StringUtils.isNotBlank(userId), Tag::getUserId, userId)
                .orderByDesc(Tag::getCrtTm));
    }

    @Override
    public Boolean saveTag(Tag tag) {
        tag.setUserId(SecurityUtils.getUser().getId());
        Tag t = getOne(Wrappers.<Tag>lambdaQuery().eq(Tag::getName, tag.getName()));
        AssertUtil.isNull(t, "该标签名称已存在");
        return save(tag);
    }
}
