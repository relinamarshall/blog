package com.blog.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.sys.entity.Blog;
import com.blog.sys.model.dto.BlogTagDto;
import com.blog.sys.model.vo.BlogNeedInfo;
import com.blog.sys.model.vo.BlogTagVo;

import java.util.List;

/**
 * BlogService
 *
 * @author Wenzhou
 * @since 2023/6/23 17:59
 */
public interface BlogService extends IService<Blog> {
    /**
     * 获取用户文章列表
     *
     * @return List<Blog>
     */
    List<Blog> getBlogListByUserId();

    /**
     * 查询文章和标签
     *
     * @param page Page<BlogTagVo>
     * @param blog Blog
     * @return Page<BlogTagVo>
     */
    Page<BlogTagVo> getBlogTagVosPage(Page<BlogTagVo> page, BlogTagVo blog);

    /**
     * getBlogTagVoById
     *
     * @param id String
     * @return BlogTagVo
     */
    BlogTagVo getBlogTagVoById(String id);

    /**
     * saveBlogTagVo
     *
     * @param blogTagDto BlogTagVo
     * @return Boolean
     */
    Boolean saveBlogTagVo(BlogTagDto blogTagDto);

    /**
     * updateBlogTagVo
     *
     * @param blogTagDto BlogTagVo
     * @return Boolean
     */
    Boolean updateBlogTagVo(BlogTagDto blogTagDto);

    /**
     * getOtherInfo
     *
     * @return BlogNeedInfo
     */
    BlogNeedInfo getOtherInfo();
}
