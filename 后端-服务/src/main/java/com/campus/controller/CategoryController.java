package com.campus.controller;

import com.campus.model.dto.CategoryDto;
import com.campus.service.CategoryService;
import com.campus.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * 分类控制器
 * @author shifuf
 * @date 2024/9/21 下午4:30
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 添加分类
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody CategoryDto categoryDto){
        return categoryService.add(categoryDto);
    }

    /**
     * 分类列表
     */
    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String name){
        return categoryService.list(pageNum,pageSize,name);
    }


    /**
     * 编辑分类
     */
    @PostMapping("/edit")
    public Result<?>  edit(@RequestBody CategoryDto categoryDto){
        return categoryService.edit(categoryDto);
    }
    /**
     * 根据ID查询分类详情
     * */
    @GetMapping("/detail")
    public Result<?> detail(@RequestParam Integer id){
        return categoryService.detail(id);
    }

    /**
     * 删除分类
     * */
    @GetMapping("/delete")
    public Result<?> delete(@RequestParam Integer id){
        return categoryService.delete(id);
    }

    /**
     * 根据分类的id查询关联的文章表
     * */
    @GetMapping("/articleList")
    public Result<?> articleList(@RequestParam(required = false) Integer id, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return categoryService.articleList(id,pageNum,pageSize);
    }

    /**
    * 先获取分类列表，再根据分类id获取文章列表
    * */
    @GetMapping("/listArticle")
    public Result<?> listArticle(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize){
        return categoryService.listArticle(pageNum,pageSize);
    }
}
