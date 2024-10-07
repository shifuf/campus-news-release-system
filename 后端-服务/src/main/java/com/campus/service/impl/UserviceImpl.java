package com.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.mapper.UserMapper;
import com.campus.model.entity.Category;
import com.campus.model.from.UserFrom;
import com.campus.model.vo.PageVo;
import com.campus.model.vo.UserInfoVO;
import com.campus.model.vo.UserPageVo;
import com.campus.model.entity.User;
import com.campus.service.UserService;
import com.campus.util.BeanCopyUtils;
import com.campus.util.Md5Util;
import com.campus.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/20 下午8:46
 */
@Service
public class UserviceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询是否存在
     */
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }


    /**
     * 注册
     */
    @Override
    public void register(String username, String password) {
        //1.先加密密码
        String md5String =  Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    /**
     * 用户列表
     * */
    @Override
    public Result<?> userList(Integer pageNum, Integer pageSize, String username) {
        //username不强制要求传
        Assert.notNull(pageNum, "pageNum不能为空");
        Assert.notNull(pageSize, "pageSize不能为空");
        //1.定义查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //1.定义查询条件
        if (username != null && !username.isEmpty()) {
            wrapper.like(User::getUsername, username);
        }
        //2.顺序排序
        wrapper.orderByAsc(User::getId);
        //2.1筛选出未删除的
        wrapper.eq(User::getIsdeleted, 0);
        //3.分页查询
        Page<User> page = new Page<>(pageNum,pageSize);
        //3.1设置当前页和每页显示的条数
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
        page(page, wrapper);

//        List<User> records = page.getRecords();
        //5.封装数据
//        UserPageVo userPageVo = new UserPageVo();
//        userPageVo.setPageNum((int) page.getCurrent());
//        userPageVo.setPageSize((int) page.getSize());
//        userPageVo.setRows(records);
        //4.获取数据
        List<UserInfoVO> users = BeanCopyUtils.copyBeanList(page.getRecords(), UserInfoVO.class);
        //5.脱敏- 封装拷贝数据返回前端
        PageVo userPageVo = new PageVo(pageNum, pageSize, page.getTotal(), users);
        //6.返回数据
        return Result.success(userPageVo);
    }

    /**
    * 新增用户
    */

    @Override
    public boolean saveUser(UserFrom userForm) {
        //1.先查询用户名是否存在
        User userName = userMapper.findByUserName(userForm.getUsername());
        if (userName != null) {
            Result.failed("用户名已存在");
            return false;
        }
        //2.加密密码
        User user = new User();
        String md5String = Md5Util.getMD5String("123456");
        user.setPassword(md5String);
        user.setUsername(userForm.getUsername());
        user.setNickname(userForm.getNickname());
        user.setAvatar(userForm.getAvatar());
        user.setType(userForm.getType());
        user.setCreatetime(new Date());
        user.setUpdatetime(new Date());
        //3.新增用户
        return save(user);
    }


    /**
     * 编辑用户
     * @param userFrom
     * @return updateById(user);
     */
    @Override
    public Result<?> edit(UserFrom userFrom) {
        //1.根据id查询是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,userFrom.getId());
        User user = getOne(wrapper);
        if (user == null) {
            return Result.failed("用户不存在");
        }
        //不做修改密码 只重置密码
        //2. 封装拷贝数据返回前端
        User user1 = BeanCopyUtils.copyBean(userFrom, User.class);
        updateById(user1);
        return Result.success(user1,"编辑成功");
    }

    @Override
    public Result<?> delete(Integer id) {
        //1.根据id查询是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,id);
        User user = getOne(wrapper);
        if (user == null) {
            return Result.failed("用户不存在");
        }
        //2.逻辑删除
        user.setIsdeleted(1);
        updateById(user);
        return Result.success("删除成功");
    }


}
