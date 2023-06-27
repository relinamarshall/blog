package com.blog.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.util.AssertUtil;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Category;
import com.blog.sys.mapper.CategoryMapper;
import com.blog.sys.service.CategoryService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * CategoryServiceImpl
 *
 * @author Wenzhou
 * @since 2023/6/23 16:23
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     * categoryMapper
     */
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryListByUserId() {
        String userId = SecurityUtils.getUser().getId();
        return categoryMapper.selectList(Wrappers.<Category>lambdaQuery()
                .eq(StringUtils.isNotBlank(userId), Category::getUserId, userId)
                .orderByDesc(Category::getCrtTm));
    }

    @Override
    public Boolean saveCategory(Category category) {
        category.setUserId(SecurityUtils.getUser().getId());
        Category t = getOne(Wrappers.<Category>lambdaQuery().eq(Category::getName, category.getName()));
        AssertUtil.isNull(t, "该分类名称已存在");
        return save(category);
    }
}
