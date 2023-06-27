package com.blog.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.sys.entity.Category;

import org.apache.ibatis.annotations.Mapper;

/**
 * CategoryMapper
 *
 * @author Wenzhou
 * @since 2023/6/23 16:24
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
