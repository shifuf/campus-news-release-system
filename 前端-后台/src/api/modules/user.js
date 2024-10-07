import {get,post} from "../../utils/request";


//用户登录
export const UserLogin = (data) => {
    return get("/user/login", data);
}

// 用户列表
export const UserList = (data) => {
    return get("/user/list", data);
    };

    //新增用户
export const UserAdd = (data) => {
    return post("/user/add",data );
}

//编辑用户
export const UserEdit = (data) => {
    return post("/user/edit", data);
}

//删除用户
export const UserDel = (params) => {
    return get("/user/delete", params);
}