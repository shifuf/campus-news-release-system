package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.model.entity.Category;
import com.campus.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午4:32
 */
@Mapper
public interface CategoryMapper    extends BaseMapper<Category> {
}
