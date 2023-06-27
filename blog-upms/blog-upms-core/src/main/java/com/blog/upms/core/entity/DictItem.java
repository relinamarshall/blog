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
 * DictItem
 *
 * @author Wenzhou
 * @since 2023/6/19 18:15
 */
@Data
@TableName("t_dict_item")
@Schema(description = "字典配置项")
@EqualsAndHashCode(callSuper = true)
public class DictItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典项主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "字典项主键")
    private String id;

    /**
     * 所属字典主键
     */
    @Schema(description = "所属字典主键")
    private String dictId;

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
     * Icon类型
     */
    @Schema(description = "Icon类型")
    private String type;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String des;

    /**
     * 删除标记
     */
    @TableLogic
    private String delFlag;
}
