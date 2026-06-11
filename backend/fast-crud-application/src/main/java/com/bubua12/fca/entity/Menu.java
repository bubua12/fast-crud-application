package com.bubua12.fca.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单实体（树形结构）
 *
 * @author bubua12
 * @since 2026/6/9
 */
@Data
@TableName("sys_menu")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {

    /** 菜单ID（主键，自增） */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 父菜单ID，顶级菜单为0 */
    private Long parentId;

    /** 菜单名称 */
    private String name;

    /** 路由路径 */
    private String path;

    /** 图标 */
    private String icon;

    /** 前端组件路径（对应 views/ 下的文件路径，如 "system/user/index"） */
    private String component;

    /** 排序 */
    private Integer sort;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 子菜单列表（非数据库字段，用于返回树形结构） */
    @TableField(exist = false)
    private List<Menu> children;
}
