import {get,post} from "../../utils/request";

/**
 * 分类列表接口
 * @param {*} data 
 * @returns 
 */
    export const CategoryList = (data) => {
        return get("/category/list", data);
        };

        /**
         * 根据ID获取该分类下的文章列表
         * @param {*} data 
         * @returns 
         */
    export const CategoryLArtList = (param) => {
        return get("/category/articleList", param);
        };
        /**
         * 获取分类列表，再根据分类id获取文章列表
         * @param {*} data 
         * @returns 
         */
    export const ListArticle = (param) => {
        return get("/category/listArticle", param);
        };