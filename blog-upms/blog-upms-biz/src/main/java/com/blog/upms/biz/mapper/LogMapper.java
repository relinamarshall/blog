package com.blog.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.upms.core.entity.Log;

import org.apache.ibatis.annotations.Mapper;

/**
 * SysLogMapper
 * <p>
 * 日志表 Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:08
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

}
