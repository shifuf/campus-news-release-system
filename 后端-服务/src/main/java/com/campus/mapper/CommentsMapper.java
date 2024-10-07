package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.model.entity.Comments;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/25 下午7:32
 */
@Mapper
public interface  CommentsMapper  extends BaseMapper<Comments> {
}
