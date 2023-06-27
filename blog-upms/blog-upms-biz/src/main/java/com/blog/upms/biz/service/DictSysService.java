package com.blog.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.core.util.R;
import com.blog.upms.core.entity.Dict;

/**
 * DictSysService
 * <p>
 * 系统参数配置
 *
 * @author Wenzhou
 * @since 2023/6/20 15:32
 */
public interface DictSysService extends IService<Dict> {
    /**
     * 通过key查询公共参数指定值
     *
     * @param key String
     * @return R
     */
    String getSysCfgKeyToValue(String key);

    /**
     * 更新参数
     *
     * @param dict Dict
     * @return R
     */
    R updateParam(Dict dict);

    /**
     * 删除参数
     *
     * @param key String
     * @return R
     */
    R removeParam(String key);

    /**
     * 同步缓存
     *
     * @return R
     */
    R syncParamCache();
}
