package com.campus.model.from;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.campus.model.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * 用户表单对象
 * @author shifuf
 * @date 2024/9/21 下午2:13
 */
@Data
public class UserFrom extends User {
    //用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long  id;

    //用户名
    private String username;
    //密码
    /*private String password;*/
    //昵称
    private String nickname;

    //用户头像
    private String avatar;

    //用户类型 0 管理员 1 普通用户
    private Integer type;

}
