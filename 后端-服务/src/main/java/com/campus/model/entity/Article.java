package com.campus.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


import java.util.Date;

@Data
public class Article {
//id    @TableId
    @TableId(type = IdType.AUTO)
    private Long id;
    //文章标题
    private String title;
    //文章内容
    private String content;
    //作者
    private String author;
    //封面图像
    private String coverimg;
    //发布状态
    private Integer state;
    //文章分类id
    private Integer categoryid;
    //分类名称
    private String categoryname;
    //文章是否推荐 0否 1是
    private Integer stop;
    //字段自增
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    //字段自增
    @TableField( fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    //关联的分类
    @TableField(exist = false)//表示不是数据库字段
    private Category category;
}
