package com.campus.service;

import com.campus.model.from.UserFrom;
import com.campus.model.vo.UserPageVo;
import com.campus.model.entity.User;
import com.campus.util.result.Result;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/20 下午8:45
 */
public interface UserService {
    /**
     * 根据用户名查询是否存在
     */
    User findByUserName(String username);

    /**
    * 注册
    */
    void register(String username, String password);

    /**
     * 用户列表
     * */
    Result<?> userList(Integer pageNum, Integer pageSize, String username);

    boolean saveUser(UserFrom userFrom);

    Result<?> edit(UserFrom userFrom);

    Result<?> delete(Integer id);
}
