package com.campus.controller;

import com.campus.model.entity.Article;
import com.campus.service.ArticleService;
import com.campus.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * 文章控制器
 * @author shifuf
 * @date 2024/9/21 下午8:19
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     * */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Article article){
        return articleService.ArticleAdd(article);
    }

    /**
     * 文章列表
     * */
    @GetMapping("/list")
    public Result<?> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String title){
        return articleService.ArticleList(pageNum,pageSize,title);
    }

    /**
     * 根据ID查询文章详情
     * */
    @GetMapping("/detail")
    public Result<?> detail(@RequestParam Long id) {
        return articleService.ArticleDetail(id);
    }

    /**
     * 编辑-更新文章
     * */
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody Article article){
        return articleService.ArticleEdit(article);
    }

    /**
     * 删除文章
     * */
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam Long id){
        return articleService.ArticleDelete(id);
    }
}
