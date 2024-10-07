import {get,post} from "../../utils/request";

// 评论列表接口
export const CommentList = (data) => {
    return get("/comments/list", data);
}
//添加留言
export const AddComment = (data) => {
    return post("/comments/add", data);
}
//编辑留言
export const EditComment = (data) => {
    return post("/comments/edit", data);
}
//删除留言
export const DelComment = (data) => {
    return get("/comments/delete", data);
}