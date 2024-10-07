package com.campus.service;


import com.campus.model.entity.Article;
import com.campus.util.result.Result;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午8:20
 */

public interface ArticleService {

    Result<?> ArticleAdd(Article article);

    Result<?> ArticleList(Integer pageNum, Integer pageSize,String title);

    Result<?> ArticleDetail(Long id);

    Result<?> ArticleEdit(Article article);

    Result<?> ArticleDelete(Long id);

}
