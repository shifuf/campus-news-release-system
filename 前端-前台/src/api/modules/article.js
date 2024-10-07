import {get,post} from "../../utils/request";
/**
 * 根据id获取文章详情
 */
export const ArticleDetail = (param) => {
    return get("/article/detail", param);
}

/**
 * 获取文章列表-可分页-可根据标题title搜索
 */
export const ArticleList = (param) => {
    return get("/article/list", param);
}