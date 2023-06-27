package com.blog.upms.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.upms.core.entity.Dict;

import org.apache.ibatis.annotations.Mapper;

/**
 * DictMapper
 * <p>
 * 字典表 Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:08
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

}
