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
 *
 * @author shifuf
 * @date 2024/9/25 下午5:01
 */
@Data
public class Comments {
    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //被回复人
    private Long pid;
    //留言人
    private String username;
    //头像
    private String avatar;
    //留言内容
    private String content;
    //字段自增
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    //字段自增
    @TableField( fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;

    @TableField(exist = false)
    private List<?> children;  // 回复列表
}
