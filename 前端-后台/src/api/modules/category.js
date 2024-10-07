import {get,post} from "../../utils/request";

// 分类列表
export const CategoryList = (data) => {
    return get("/category/list", data);
}
//新增分类
export const CategoryAdd = (data) => {
    return post("/category/add",data );
}
//编辑分类
export const CategoryEdit = (data) => {
    return post("/category/edit", data);
}
//删除分类
export const CategoryDel = (params) => {
    return get("/category/delete", params);
}