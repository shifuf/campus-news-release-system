import {get,post} from "../../utils/request";

// 文章列表
export const ArticleList = (data) => {
    return get("/article/list", data);
}
//文章详情 
export const ArticleDetail = (params) => {
    return get("/article/detail", params);
}
//新增文章
export const ArticleAdd = (data) => {
    return post("/article/add",data );
}
//编辑文章
export const ArticleEdit = (data) => {
    return post("/article/edit", data);
}
//删除文章
export const ArticleDel = (params) => {
    return get("/article/delete", params);
}
