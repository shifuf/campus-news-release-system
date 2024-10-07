package com.campus.service;

import com.campus.model.entity.Comments;
import com.campus.util.result.Result;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/25 下午7:33
 */
public interface CommentsService {
    Result<?> CommentsList(Integer pageNum, Integer pageSize);

    Result<?> addComments(Comments comments);

    Result<?> editComments(Comments comments);

    Result<?> deleteComments(Integer id);
}
