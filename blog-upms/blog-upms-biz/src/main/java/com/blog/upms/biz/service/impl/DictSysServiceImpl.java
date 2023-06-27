package com.blog.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.common.core.constant.enums.DictTypeEnum;
import com.blog.common.core.exception.ErrorCodes;
import com.blog.common.core.util.MsgUtil;
import com.blog.common.core.util.R;
import com.blog.upms.biz.mapper.DictMapper;
import com.blog.upms.biz.service.DictSysService;
import com.blog.upms.core.entity.Dict;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * DictSysServiceImpl
 *
 * @author Wenzhou
 * @since 2023/6/20 15:40
 */
@Service
@RequiredArgsConstructor
public class DictSysServiceImpl extends ServiceImpl<DictMapper, Dict>
        implements DictSysService {
    @Override
    @Cacheable(value = CacheConstants.PARAMS_DETAILS, key = "#code", unless = "#result == null ")
    public String getSysCfgKeyToValue(String code) {
        Dict dict = this.baseMapper
                .selectOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getCode, code));

        if (dict != null) {
            return dict.getContent();
        }
        return null;
    }

    /**
     * 更新参数
     *
     * @param dict Dict
     * @return R
     */
    @Override
    @CacheEvict(value = CacheConstants.PARAMS_DETAILS, key = "#dict.code")
    public R updateParam(Dict dict) {
        Dict param = this.getById(dict.getCode());
        // 系统内置
        if (DictTypeEnum.SYS.getType().equals(param.getSysCfgFlag())) {
            return R.failed(MsgUtil.getMessage(ErrorCodes.SYS_PARAM_DELETE_SYSTEM));
        }
        return R.ok(this.updateById(dict));
    }

    /**
     * 删除参数
     *
     * @param id String
     * @return R
     */
    @Override
    @CacheEvict(value = CacheConstants.PARAMS_DETAILS, allEntries = true)
    public R removeParam(String id) {
        Dict param = this.getById(id);
        // 系统内置
        if (DictTypeEnum.SYS.getType().equals(param.getSysCfgFlag())) {
            return R.failed("系统内置参数不能删除");
        }
        return R.ok(this.removeById(id));
    }

    /**
     * 同步缓存
     *
     * @return R
     */
    @Override
    @CacheEvict(value = CacheConstants.PARAMS_DETAILS, allEntries = true)
    public R syncParamCache() {
        return R.ok();
    }
}
