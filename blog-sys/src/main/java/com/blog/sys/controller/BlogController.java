package com.blog.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Blog;
import com.blog.sys.model.dto.BlogTagDto;
import com.blog.sys.model.vo.BlogNeedInfo;
import com.blog.sys.model.vo.BlogTagVo;
import com.blog.sys.service.BlogService;

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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * BlogController
 *
 * @author Wenzhou
 * @since 2023/6/23 17:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
@Tag(name = "博客系统文章管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class BlogController {
    /**
     * blogService
     */
    private final BlogService blogService;

    /**
     * 分页查询文章信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<BlogTagVo>> getBlogTagVosPage(Page<BlogTagVo> page, BlogTagVo blog) {
        return R.ok(blogService.getBlogTagVosPage(page, blog));
    }

    /**
     * 查询文章列表
     *
     * @return R
     */
    @GetMapping("/list")
    public R<List<Blog>> getBlogList() {
        return R.ok(blogService.getBlogListByUserId());
    }

    /**
     * 查询单文章列表
     *
     * @return R
     */
    @GetMapping("/{id}")
    public R<BlogTagVo> getBlogList(@PathVariable("id") String id) {
        return R.ok(blogService.getBlogTagVoById(id));
    }

    /**
     * 添加文章
     *
     * @param blogTagDto Blog
     * @return R
     */
    @Log("添加文章")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('blog_blog_add')")
    public R<Void> save(@Valid @RequestBody BlogTagDto blogTagDto) {
        blogTagDto.setUserId(SecurityUtils.getUser().getId());
        return blogService.saveBlogTagVo(blogTagDto) ? R.ok() : R.failed();
    }

    /**
     * 修改文章
     *
     * @param blogTagDto 文章信息
     * @return success/false
     */
    @PutMapping
    @Log("修改文章")
    @PreAuthorize("@pms.hasPermission('blog_blog_edit')")
    public R<Void> updateById(@Valid @RequestBody BlogTagDto blogTagDto) {
        return blogService.updateBlogTagVo(blogTagDto) ? R.ok() : R.failed();
    }

    /**
     * 删除文章
     *
     * @param id String
     * @return R
     */
    @Log("删除文章")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('blog_blog_del')")
    public R<Void> removeById(@PathVariable String id) {
        return blogService.removeById(id) ? R.ok() : R.failed();
    }

    /**
     * 获取文章相关信息
     *
     * @return BlogNeedInfo
     */
    @GetMapping("/other/info")
    public R<BlogNeedInfo> getOtherInfo() {
        return R.ok(blogService.getOtherInfo());
    }
}
