package com.blog.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.sys.entity.Category;

import java.util.List;

/**
 * CategoryService
 *
 * @author Wenzhou
 * @since 2023/6/23 16:20
 */
public interface CategoryService extends IService<Category> {
    /**
     * 获取用户分类列表
     *
     * @return List<Category>
     */
    List<Category> getCategoryListByUserId();

    /**
     * 添加分类
     *
     * @param category Category
     * @return Boolean
     */
    Boolean saveCategory(Category category);
}
