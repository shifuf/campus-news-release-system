package com.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.mapper.ArticleMapper;
import com.campus.mapper.CategoryMapper;
import com.campus.model.entity.Article;
import com.campus.model.entity.Category;
import com.campus.model.vo.PageVo;
import com.campus.service.ArticleService;
import com.campus.util.BeanCopyUtils;
import com.campus.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午8:20
 */
@Service
public class ArticleImpl  extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;


    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public Result<?> ArticleAdd(Article article) {
        //1.根据文章标题查询是否存在
        //1.2.不存在则添加
//        LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper<>();
//        queryWrapper.eq(Article::getTitle,article.getTitle());
//        Article article1 = getOne(queryWrapper);
//        if (article1 != null) {
//            return Result.failed("文章标题已存在");
//        }
        //2.保存文章
        return Result.success(save(article),"添加成功");
    }

    //MyBatis Plus
    @Override
    public Result<?> ArticleList(Integer pageNum, Integer pageSize, String title) {
        //1.定义查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //根据传递过来的标题查询
        if (title != null && !title.isEmpty()) {
            wrapper.like(Article::getTitle,title);
        }
        //2.顺序排序
        wrapper.orderByAsc(Article::getId);
        //3.分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        //3.1设置当前页和每页显示的条数
        page(page, wrapper);
        //4.关联分类表返回数据
        List<Article> records = page.getRecords();
        for (Article record : records) {
            Category category = categoryMapper.selectById(record.getCategoryid());
            record.setCategory(category);
            record.setCategoryname(category.getName());
        }
        //5. 封装拷贝数据返回前端
        List<Article> articles = BeanCopyUtils.copyBeanList(page.getRecords(), Article.class);
        PageVo pageVo = new PageVo(pageNum,pageSize,page.getTotal(),articles);
        //6.返回数据
        return Result.success(pageVo,"查询成功");
    }

    @Override
    public Result<?> ArticleDetail(Long id) {
        //1.根据id查询是否存在

        // 查询文章
        Article article = articleMapper.selectById(id);
        if (getById(id) == null) {
            return Result.failed("文章不存在");
        }
        // 查询文章的分类信息
        if (article != null && article.getId() != null) {
            Category category = categoryMapper.selectById(article.getCategoryid());
            article.setCategory(category);  // 设置分类信息到文章对象
        }

        return Result.success(article);
    }

    @Override
    public Result<?> ArticleEdit(Article article) {
        if (getById(article.getId()) == null) {
            return Result.failed("文章不存在");
        }
        //2.更新文章
        return Result.success(updateById(article),"编辑成功");
    }

    @Override
    public Result<?> ArticleDelete(Long id) {
        //1.根据id查询是否存在
        if (getById(id) == null) {
            return Result.failed("文章不存在");
        }
        //2.删除文章

        return Result.success(removeById(id));
    }


}
