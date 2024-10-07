import {get,post} from "../../utils/request";

//测试接口
export const test = (data) => {
    return get("/user/list", data);
    };

    //登录
export const userLogin = (data) => {
    return get("/user/login", data);
    };