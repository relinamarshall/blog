package com.blog.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.sys.entity.Blog;
import com.blog.sys.model.vo.BlogTagVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * BlogMapper
 *
 * @author Wenzhou
 * @since 2023/6/23 18:00
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 获取文章标签
     *
     * @param page Page<BlogTagVo>
     * @param blog BlogTagVo
     * @return Page<BlogTagVo>
     */
    Page<BlogTagVo> getBlogTagVosPage(Page<BlogTagVo> page, @Param("q") BlogTagVo blog);

    /**
     * getBlogTagVoById
     *
     * @param id String
     * @return BlogTagVo
     */
    BlogTagVo getBlogTagVoById(@Param("id") String id);
}
