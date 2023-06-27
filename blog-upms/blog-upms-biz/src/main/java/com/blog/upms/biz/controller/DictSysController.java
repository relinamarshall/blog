package com.blog.upms.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.constant.enums.DictTypeEnum;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.common.security.annotation.Inner;
import com.blog.upms.biz.service.DictSysService;
import com.blog.upms.core.entity.Dict;

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

import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * DictController
 *
 * @author Wenzhou
 * @since 2023/6/20 15:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
@Tag(name = "字典系统配置")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DictSysController {

    private final DictSysService dictSysService;

    /**
     * 通过key查询系统参数值
     *
     * @param type String
     * @return R
     */
    @Inner(value = false)
    @Operation(summary = "查询系统参数值", description = "查询系统参数值")
    @GetMapping("/type/{type}")
    public R publicKey(@PathVariable("type") String type) {
        return R.ok(dictSysService.getSysCfgKeyToValue(type));
    }

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @param dict 系统参数
     * @return R
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R getPublicParamPage(Page<Dict> page, Dict dict) {
        return R.ok(dictSysService.page(page, Wrappers.<Dict>lambdaQuery()
                .eq(Dict::getSysCfgFlag, DictTypeEnum.SYS.getType())
                .like(CharSequenceUtil.isNotBlank(dict.getCode()), Dict::getCode, dict.getCode())
                .orderByDesc(Dict::getCrtTm)));
    }

    /**
     * 通过id查询系统参数
     *
     * @param id String
     * @return R
     */
    @Operation(summary = "通过id查询系统参数", description = "通过id查询系统参数")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(dictSysService.getById(id));
    }

    /**
     * 新增系统参数
     *
     * @param dict 系统参数
     * @return R
     */
    @Operation(summary = "新增系统参数", description = "新增系统参数")
    @Log("新增系统参数")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_cfg_add')")
    public R save(@RequestBody Dict dict) {
        return R.ok(dictSysService.save(dict));
    }

    /**
     * 修改系统参数
     *
     * @param dict 系统参数
     * @return R
     */
    @Operation(summary = "修改系统参数", description = "修改系统参数")
    @Log("修改系统参数")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_cfg_edit')")
    public R updateById(@RequestBody Dict dict) {
        return dictSysService.updateParam(dict);
    }

    /**
     * 通过id删除系统参数
     *
     * @param id String
     * @return R
     */
    @Operation(summary = "删除系统参数", description = "删除系统参数")
    @Log("删除系统参数")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_cfg_del')")
    public R removeById(@PathVariable String id) {
        return dictSysService.removeParam(id);
    }

    /**
     * 同步参数
     *
     * @return R
     */
    @Log("同步系统参数")
    @PutMapping("/sync")
    public R sync() {
        return dictSysService.syncParamCache();
    }
}
