package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午8:20
 */
@Mapper
public interface ArticleMapper    extends BaseMapper<Article> {
}
