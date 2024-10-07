package com.campus.controller;

import com.campus.model.entity.Comments;
import com.campus.service.CommentsService;
import com.campus.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/25 下午7:33
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    // 获取所有留言及其回复
    @GetMapping("/list")
    public Result<?> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return commentsService.CommentsList(pageNum,pageSize);
    }

    // 添加留言
    @PostMapping("/add")
    public Result<?> add(@RequestBody Comments comments){
        return commentsService.addComments(comments);
    }

    //编辑留言内容
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody Comments comments){
        return commentsService.editComments(comments);
    }

    //删除留言
    @GetMapping("/delete")
    public Result<?> delete(@RequestParam Integer id){
        return commentsService.deleteComments(id);
    }
}
