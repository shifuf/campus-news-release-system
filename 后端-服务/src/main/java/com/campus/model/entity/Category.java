package com.campus.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * 分类实体类
 * @author shifuf
 * @date 2024/9/21 下午4:28
 */
@Data
public class Category {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //分类名称
    private String name;
    private Long articlecount;
    //字段自增
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    //字段自增
    @TableField( fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    //关联的分类
    @TableField(exist = false)//表示不是数据库字段
    private List<?> article;
}
