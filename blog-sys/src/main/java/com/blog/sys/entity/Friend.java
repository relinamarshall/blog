package com.blog.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.core.mybatis.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Friend
 * <p>
 * 友链
 *
 * @author Wenzhou
 * @since 2023/6/23 13:07
 */
@Data
@TableName("b_friend")
@Schema(description = "友链")
@EqualsAndHashCode(callSuper = true)
public class Friend extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "主键")
    private String id;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    private String userId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String des;

    /**
     * 站点
     */
    @Schema(description = "站点")
    private String website;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 浏览次数
     */
    @Schema(title = "浏览次数")
    private Integer views;

    /**
     * 公开或私密
     */
    @Schema(title = "公开或私密")
    private String pubFlag;

    /**
     * 删除标记 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;
}
