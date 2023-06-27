package com.blog.upms.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.core.mybatis.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Dict
 *
 * @author Wenzhou
 * @since 2023/6/19 18:15
 */
@Data
@TableName("t_dict")
@Schema(description = "字典配置")
@EqualsAndHashCode(callSuper = true)
public class Dict extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "字典主键")
    private String id;

    /**
     * 编码
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String des;

    /**
     * 系统配置标记
     */
    @Schema(description = "系统配置标记")
    private String sysCfgFlag;

    /**
     * 删除标记
     */
    @TableLogic
    private String delFlag;

}
