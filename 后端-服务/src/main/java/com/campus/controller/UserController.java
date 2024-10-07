package com.campus.controller;

import com.campus.model.entity.User;
import com.campus.model.from.UserFrom;
import com.campus.model.vo.UserInfoVO;
import com.campus.service.UserService;
import com.campus.util.BeanCopyUtils;
import com.campus.util.Md5Util;
import com.campus.util.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/20 下午9:20
 */
@RestController
@RequestMapping("/user")
public class UserController {


   @Autowired
   private UserService userService;

   @GetMapping("/test")
    public Result test() {
         return Result.success("test success");
    }


    @PostMapping("/register")
    /*
    * 注册
    * */
    public Result register(String username, String password) {
        //1.查询数据库中用户名是否已存在
        User user = userService.findByUserName(username);
        //1.1 不存在直接注册
        if (user == null) {
            userService.register(username, password);
            return Result.success("注册成功");
        }else {
            //1.2如果存在，返回用户名已存在
            return Result.failed("用户名已存在");
        }
    }

    /*
    * 登录
    * */
    @GetMapping("/login")
    public Result<?> login(String username,String password) {
        //1.查询数据库中用户名是否已存在
        User user = userService.findByUserName(username);
        if (user==null){
            return Result.failed("用户名错误");
        }
        if (Md5Util.getMD5String(password).equals(user.getPassword())) {
            //脱敏返回
            UserInfoVO userInfoVO = BeanCopyUtils.copyBean(user, UserInfoVO.class);
            return Result.success(userInfoVO,"登录成功");
        }
        
        return Result.failed("密码错误");
    }

    /*
    *
    用户列表**/
    @GetMapping("/list")
    public Result<?> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String username) {
        return userService.userList(pageNum,pageSize,username);
    }


    /*
    * 新增用户
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody UserFrom userFrom) {
        boolean result = userService.saveUser(userFrom);
        return Result.success(result);
    }

    /**
     * 编辑用户
     * @return
     */
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody UserFrom userFrom) {

        return userService.edit(userFrom);
    }


    /*
     * 用户信息
     * */
    @GetMapping("/info{id}")
    public Result info() {
        return Result.success("info success");
    }

    /*
    *删除用户
    * */
    @GetMapping("/delete")
    public Result<?> delete(@RequestParam Integer id) {
        //逻辑删除
        return userService.delete(id);
    }

}
