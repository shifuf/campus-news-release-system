package com.campus.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * 用户信息视图对象
 * @author shifuf
 * @date 2024/9/21 下午2:23
 */
@Data
public class UserInfoVO {
    //用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long  id;

    //用户名
    private String username;

    //逻辑删除
    private Integer isdeleted;

    //昵称
    private String nickname;

    //用户头像
    private String avatar;

    //用户类型 0 管理员 1 普通用户
    private Integer type;
}
