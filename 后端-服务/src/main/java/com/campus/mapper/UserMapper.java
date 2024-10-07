package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/20 下午8:46
 */
@Mapper
public interface UserMapper   extends BaseMapper<User> {
    /*
    * 用户登录
    * */
    User login(User user);


    User findByUserName(String username);

    void add(String username, String password);
}
