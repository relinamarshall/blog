package com.blog.xxl.job.admin.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * XxlJobGroup
 *
 * @author Wenzhou
 * @since 2023/5/10 20:37
 */
@Data
public class XxlJobGroup {
    private int id;

    private String appname;

    private String title;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    private int addressType;

    /**
     * 执行器地址列表，多地址逗号分隔(手动录入)
     */
    private String addressList;

    private Date updateTime;

    /**
     * 执行器地址列表(系统注册)
     */
    private List<String> registryList;

    /**
     * getRegistryList
     *
     * @return List<String>
     */
    public List<String> getRegistryList() {
        if (addressList != null && addressList.trim().length() > 0) {
            registryList = new ArrayList<String>(Arrays.asList(addressList.split(",")));
        }
        return registryList;
    }
}
