package com.blog.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.util.AssertUtil;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Blog;
import com.blog.sys.entity.BlogTag;
import com.blog.sys.mapper.BlogMapper;
import com.blog.sys.mapper.BlogTagMapper;
import com.blog.sys.model.dto.BlogTagDto;
import com.blog.sys.model.vo.BlogNeedInfo;
import com.blog.sys.model.vo.BlogTagVo;
import com.blog.sys.service.BlogService;
import com.blog.sys.service.CategoryService;
import com.blog.sys.service.TagService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * @author Wenzhou
 * @since 2023/6/23 18:00
 */
@Service
@RequiredArgsConstructor
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    /**
     * blogMapper
     */
    private final BlogMapper blogMapper;

    /**
     * tagMapper
     */
    private final BlogTagMapper blogTagMapper;

    @Override
    public List<Blog> getBlogListByUserId() {
        String userId = SecurityUtils.getUser().getId();
        return blogMapper.selectList(Wrappers.<Blog>lambdaQuery()
                .eq(StringUtils.isNotBlank(userId), Blog::getUserId, userId)
                .orderByDesc(Blog::getCrtTm));
    }

    @Override
    public Page<BlogTagVo> getBlogTagVosPage(Page<BlogTagVo> page, BlogTagVo blog) {
        return blogMapper.getBlogTagVosPage(page, blog);
    }

    @Override
    public BlogTagVo getBlogTagVoById(String id) {
        return blogMapper.getBlogTagVoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBlogTagVo(BlogTagDto blogTagDto) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogTagDto, blog);
        baseMapper.insert(blog);
        blogTagDto.getTagList().stream().map(tagId -> {
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blog.getId());
            blogTag.setTagId(tagId);
            return blogTag;
        }).forEach(blogTagMapper::insert);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateBlogTagVo(BlogTagDto blogTagDto) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogTagDto, blog);
        boolean success = this.updateById(blog);
        AssertUtil.isTrue(success, "文章更新失败!");

        blogTagMapper.delete(Wrappers.<BlogTag>update().lambda().eq(BlogTag::getBlogId, blog.getId()));
        blogTagDto.getTagList().forEach(tagId -> {
            BlogTag tag = new BlogTag();
            tag.setTagId(tagId);
            tag.setBlogId(blog.getId());
            tag.insert();
        });
        return Boolean.TRUE;
    }

    private final TagService tagService;
    private final CategoryService categoryService;

    @Override
    public BlogNeedInfo getOtherInfo() {
        String userId = SecurityUtils.getUser().getId();
        BlogNeedInfo info = new BlogNeedInfo();
        info.setCategoryList(categoryService.list(Wrappers.emptyWrapper()));
        info.setTagList(tagService.list(Wrappers.emptyWrapper()));
        return info;
    }
}
