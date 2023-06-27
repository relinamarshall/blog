package com.blog.upms.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.constant.CacheConstants;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.upms.biz.service.DictItemService;
import com.blog.upms.biz.service.DictService;
import com.blog.upms.core.entity.Dict;
import com.blog.upms.core.entity.DictItem;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
 * DictController
 * <p>
 * 字典表 前端控制器
 *
 * @author Wenzhou
 * @since 2023/5/12 10:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dict")
@Tag(name = "字典管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DictController {

    private final DictItemService dictItemService;

    private final DictService dictService;

    /**
     * 通过ID查询字典信息
     *
     * @param id ID
     * @return 字典信息
     */
    @GetMapping("/{id}")
    public R<Dict> getById(@PathVariable String id) {
        return R.ok(dictService.getById(id));
    }

    /**
     * 分页查询字典信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<Dict>> getDictPage(Page<Dict> page, Dict dict) {
        return R.ok(dictService.page(page, Wrappers.<Dict>lambdaQuery()
                .eq(CharSequenceUtil.isNotBlank(dict.getSysCfgFlag()), Dict::getSysCfgFlag, dict.getSysCfgFlag())
                .like(CharSequenceUtil.isNotBlank(dict.getCode()), Dict::getCode, dict.getCode())
                .orderByDesc(Dict::getCrtTm)
        ));
    }

    /**
     * 通过字典类型查找字典
     *
     * @param key 类型
     * @return 同类型字典
     */
    @GetMapping("/key/{code}")
    @Cacheable(value = CacheConstants.DICT_DETAILS, key = "#code")
    public R<List<DictItem>> getDictByCode(@PathVariable String code) {
        Dict dict = dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getCode, code));
        Assert.notNull(dict, "未找到字典配置项[" + code + "]");
        return R.ok(dictItemService.list(Wrappers.<DictItem>query().lambda().eq(DictItem::getDictId, dict.getId())));
    }

    /**
     * 添加字典
     *
     * @param sysDict 字典信息
     * @return success、false
     */
    @Log("添加字典")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_dict_add')")
    public R<Boolean> save(@Valid @RequestBody Dict sysDict) {
        return R.ok(dictService.save(sysDict));
    }

    /**
     * 删除字典，并且清除字典缓存
     *
     * @param id ID
     * @return R
     */
    @Log("删除字典")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_dict_del')")
    public R removeById(@PathVariable String id) {
        dictService.removeDict(id);
        return R.ok();
    }

    /**
     * 修改字典
     *
     * @param sysDict 字典信息
     * @return success/false
     */
    @PutMapping
    @Log("修改字典")
    @PreAuthorize("@pms.hasPermission('sys_dict_edit')")
    public R updateById(@Valid @RequestBody Dict sysDict) {
        dictService.updateDict(sysDict);
        return R.ok();
    }

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param sysDictItem 字典项
     * @return IPage<DictItem>
     */
    @GetMapping("/item/page")
    public R<IPage<DictItem>> getDictItemPage(Page<DictItem> page, DictItem sysDictItem) {
        return R.ok(dictItemService.page(page, Wrappers.<DictItem>query(sysDictItem).orderByAsc("code")));
    }

    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return R
     */
    @GetMapping("/item/{id}")
    public R<DictItem> getDictItemById(@PathVariable("id") String id) {
        return R.ok(dictItemService.getById(id));
    }

    /**
     * 新增字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @Log("新增字典项")
    @PostMapping("/item")
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R<Boolean> save(@RequestBody DictItem sysDictItem) {
        return R.ok(dictItemService.save(sysDictItem));
    }

    /**
     * 修改字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @Log("修改字典项")
    @PutMapping("/item")
    public R updateById(@RequestBody DictItem sysDictItem) {
        dictItemService.updateDictItem(sysDictItem);
        return R.ok();
    }

    /**
     * 通过id删除字典项
     *
     * @param id id
     * @return R
     */
    @Log("删除字典项")
    @DeleteMapping("/item/{id}")
    public R removeDictItemById(@PathVariable String id) {
        dictItemService.removeDictItem(id);
        return R.ok();
    }

    /**
     * clearDictCache
     *
     * @return R
     */
    @Log("清除字典缓存")
    @DeleteMapping("/cache")
    @PreAuthorize("@pms.hasPermission('sys_dict_del')")
    public R clearDictCache() {
        dictService.clearDictCache();
        return R.ok();
    }
}

