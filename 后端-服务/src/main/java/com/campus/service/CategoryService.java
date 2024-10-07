package com.campus.service;

import com.campus.model.dto.CategoryDto;
import com.campus.model.entity.Category;
import com.campus.util.result.Result;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午4:31
 */
public interface CategoryService {
    //添加分类
    Result<?> add(CategoryDto categoryDto);

    Result<?> list(Integer pageNum, Integer pageSize,String name);

    Result<?> edit(CategoryDto categoryDto);

    Result<?> detail(Integer id);

    Result<?> delete(Integer id);

    Result<?> articleList(Integer id, Integer pageNum, Integer pageSize);

    Result<?> listArticle(Integer pageNum, Integer pageSize);
}
