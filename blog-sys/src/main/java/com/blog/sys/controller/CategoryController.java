package com.blog.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.sys.entity.Category;
import com.blog.sys.service.CategoryService;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * CategoryController
 *
 * @author Wenzhou
 * @since 2023/6/23 16:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Tag(name = "博客系统分类管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CategoryController {
    /**
     * categoryService
     */
    private final CategoryService categoryService;

    /**
     * 分页查询分类信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<Category>> getCategoryPage(Page<Category> page,
                                              Category category) {
        return R.ok(categoryService.page(page, Wrappers.<Category>lambdaQuery()
                .like(CharSequenceUtil.isNotBlank(category.getName()), Category::getName, category.getName())));
    }

    /**
     * 查询分类列表
     *
     * @return R
     */
    @GetMapping("/list")
    public R<List<Category>> getCategoryList() {
        return R.ok(categoryService.getCategoryListByUserId());
    }

    /**
     * 查询单分类列表
     *
     * @return R
     */
    @GetMapping("/{id}")
    public R<Category> getCategoryList(@PathVariable("id") String id) {
        return R.ok(categoryService.getById(id));
    }

    /**
     * 添加分类
     *
     * @param category Category
     * @return R
     */
    @Log("添加分类")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('blog_category_add')")
    public R<Void> save(@Valid @RequestBody Category category) {
        return categoryService.saveCategory(category) ? R.ok() : R.failed();
    }

    /**
     * 修改分类
     *
     * @param category 分类信息
     * @return success/false
     */
    @PutMapping
    @Log("修改分类")
    @PreAuthorize("@pms.hasPermission('blog_category_edit')")
    public R<Void> updateById(@Valid @RequestBody Category category) {
        return categoryService.updateById(category) ? R.ok() : R.failed();
    }

    /**
     * 删除分类
     *
     * @param id String
     * @return R
     */
    @Log("删除分类")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('blog_category_del')")
    public R<Void> removeById(@PathVariable String id) {
        return categoryService.removeById(id) ? R.ok() : R.failed();
    }
}
