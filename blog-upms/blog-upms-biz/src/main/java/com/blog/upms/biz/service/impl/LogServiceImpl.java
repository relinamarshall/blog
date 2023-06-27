package com.blog.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.upms.biz.mapper.LogMapper;
import com.blog.upms.biz.service.LogService;
import com.blog.upms.core.dto.LogDto;
import com.blog.upms.core.entity.Log;

import org.springframework.stereotype.Service;

import java.util.List;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;

/**
 * LogServiceImpl
 *
 * @author Wenzhou
 * @since 2023/5/12 12:45
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public Page<Log> getLogByPage(Page<Log> page, LogDto logDto) {
        return baseMapper.selectPage(page, buildQueryWrapper(logDto));
    }

    /**
     * 列表查询日志
     *
     * @param sysLog 查询条件
     * @return List
     */
    @Override
    public List<Log> getLogList(LogDto sysLog) {
        return baseMapper.selectList(buildQueryWrapper(sysLog));
    }

    /**
     * 构建查询的 wrapper
     *
     * @param sysLog 查询条件
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<Log> buildQueryWrapper(LogDto sysLog) {
        LambdaQueryWrapper<Log> wrapper = Wrappers.<Log>lambdaQuery()
                .eq(CharSequenceUtil.isNotBlank(sysLog.getType()), Log::getType, sysLog.getType())
                .like(CharSequenceUtil.isNotBlank(sysLog.getRemoteAddr()),
                        Log::getRemoteAddr, sysLog.getRemoteAddr())
                .orderByDesc(Log::getCrtTm);

        if (ArrayUtil.isNotEmpty(sysLog.getCrtTm())) {
            wrapper.ge(Log::getCrtTm, sysLog.getCrtTm()[0])
                    .le(Log::getCrtTm, sysLog.getCrtTm()[1]);
        }

        return wrapper;
    }

}
