package com.blog.upms.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.upms.core.dto.LogDto;
import com.blog.upms.core.entity.Log;

import java.util.List;

/**
 * LogService
 * <p>
 * 日志表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface LogService extends IService<Log> {

    /**
     * 分页查询日志
     *
     * @param page   Page
     * @param sysLog LogDto
     * @return Page<Log>
     */
    Page<Log> getLogByPage(Page<Log> page, LogDto sysLog);

    /**
     * 列表查询日志
     *
     * @param sysLog 查询条件
     * @return List
     */
    List<Log> getLogList(LogDto sysLog);
}
