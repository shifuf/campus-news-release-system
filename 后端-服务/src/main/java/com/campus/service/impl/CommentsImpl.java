package com.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.mapper.CommentsMapper;
import com.campus.model.entity.Article;
import com.campus.model.entity.Comments;
import com.campus.model.vo.PageVo;
import com.campus.service.CommentsService;
import com.campus.util.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/25 下午7:33
 */
@Service
public class CommentsImpl   extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {
    @Override
    public Result<?> CommentsList(Integer pageNum, Integer pageSize) {
        //1.留言
        LambdaQueryWrapper<Comments> wrapper = new LambdaQueryWrapper<>();
        //1.2根据时间排序
        wrapper.orderByAsc(Comments::getCreatetime);
        //1.3 查询所有留言
        List<Comments> comments = list(wrapper);
        // 2.分页查询
        Page<Comments> page = new Page<>(pageNum, pageSize);
        List<Comments> records = page.getRecords();
        //4.pid为null的留言是顶级留言pid有数据为回复写入列表child
        List<Comments> collect = comments.stream().filter(comment -> comment.getPid() == null).peek(comment -> {
            List<Comments> child = comments.stream().filter(c -> c.getPid() != null && c.getPid().equals(comment.getId())).collect(Collectors.toList());
            comment.setChildren(child);
        }).toList();
        //5.分页并返回
        List<Comments> collect1 = collect.stream().skip((long) (pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        PageVo pageVo = new PageVo(pageNum,pageSize,page.getTotal(),collect1);
        return Result.success(pageVo);
    }

    @Override
    public Result<?> addComments(Comments comments) {
        //1.添加留言
        return Result.success(save(comments),"添加成功");
    }

    @Override
    public Result<?> editComments(Comments comments) {
        //1.根据id查询留言是否存在
        LambdaQueryWrapper<Comments> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Comments::getId,comments.getId());
        Comments comment = getOne(queryWrapper);
        if(comment == null){
            return Result.failed("留言不存在");
        }
        return Result.success( updateById(comments),"修改成功");
    }

    @Override
    public Result<?> deleteComments(Integer id) {
        //1.根据id查询留言是否存在
        LambdaQueryWrapper<Comments> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Comments::getId,id);
        Comments comment = getOne(queryWrapper);
        if(comment == null){
            return Result.failed("留言不存在");
        }
        return Result.success( removeById(id),"删除成功");
    }
}
