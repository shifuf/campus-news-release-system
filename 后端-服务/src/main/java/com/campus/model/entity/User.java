package com.campus.model.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * 用户实体类
 * @author shifuf
 * @date 2024/9/20 下午8:46
 */
@Data
public class User  {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //用户名
    private String username;
    //密码 返回前端不显示该字段
    private String password;
    //逻辑删除
    private Integer isdeleted;
    //昵称
    private String nickname;
    //用户头像
    private String avatar;
    //用户类型 0 管理员 1 普通用户
    private Integer type;
    //字段自增
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    //字段自增
    @TableField( fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
}
