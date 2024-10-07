package com.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.mapper.ArticleMapper;
import com.campus.mapper.CategoryMapper;
import com.campus.model.dto.CategoryDto;
import com.campus.model.entity.Article;
import com.campus.model.entity.Category;
import com.campus.model.vo.PageVo;
import com.campus.service.CategoryService;
import com.campus.util.BeanCopyUtils;
import com.campus.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午4:31
 */
@Service
public class CategoryImpl extends ServiceImpl<CategoryMapper, Category> implements   CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;
    /**
     * 添加分类
     */
    @Override
    public Result<?> add(CategoryDto categoryDto) {
        //1.根据名称查询是否存在
        //1.2.不存在则添加
        String name = categoryDto.getName();
        if (name == null || name.isEmpty()) {
            return Result.failed("分类名称不能为空");
        }
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getName,name);
        Category category = getOne(wrapper);
        if (category != null) {
            return Result.failed("分类名称已存在");
        }
        //2.根据前端传递过来的参数转换为实体类
        Category category1 =  BeanCopyUtils.copyBean(categoryDto,Category.class);
        save(category1);
        return Result.success(categoryDto);
    }

    /**
     * 分类列表
     */
    @Override
    public Result<?> list(Integer pageNum, Integer pageSize,String name) {
        //1.定义查询条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if(name != null && !name.isEmpty()){
            wrapper.like(Category::getName,name);
        }
        //2.顺序排序
        wrapper.orderByAsc(Category::getId);
        //3.分页查询
        Page<Category> page = new Page<>(pageNum,pageSize);
        //3.1设置当前页和每页显示的条数
        page(page, wrapper);
        //5. 封装拷贝数据返回前端
        //5.1获取分类下的文章列表数量
        for (Category record : page.getRecords()) {
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getCategoryid, record.getId());
            Long count = articleMapper.selectCount(articleWrapper);
            //统计分类下的文章
            record.setArticlecount(count);
        }
        List<Category> categories =BeanCopyUtils.copyBeanList(page.getRecords(), Category.class);
        PageVo pageVo = new PageVo(pageNum,pageSize,page.getTotal(),categories);
        //6.返回数据
        return Result.success(pageVo,"查询成功");
    }

    /**
     * 编辑分类
     * */
    @Override
    public Result<?> edit( CategoryDto categoryDto) {
//        //1.根据id查询是否存在
//        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Category::getName,categoryDto.getName());
//        Category category = getOne(wrapper);
//        if (category != null) {
//            return Result.failed("分类名称已存在");
//        }
        //2. 封装拷贝数据返回前端
        Category categories =BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(categories);
        return Result.success(categories,"编辑成功");
    }

    /**
     * 根据ID查询分类详情
     * */
    @Override
    public Result<?> detail(Integer id) {
        //1.根据id查询是否存在
        Category category = getById(id);
        if (category == null) {
            return Result.failed("分类不存在");
        }
        //2. 获取到的数据返回前端
        return Result.success(categoryMapper.selectById(id));
    }


    /**
    * 删除分类
    * */
    @Override
    public Result<?> delete(Integer id) {
        //1.根据id查询是否存在
        Category category = getById(id);
        if (category == null) {
            return Result.failed("分类不存在");
        }
        //2.判断是否还有文章如果有文章则不能删除
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryid,id);
        Long count = articleMapper.selectCount(wrapper);
        if (count > 0) {
            return Result.failed("该分类下还有文章不能删除");
        }
        categoryMapper.deleteById(id);
        return Result.success("删除成功");
    }
    /**
     * 根据分类的id查询关联的文章表
     * */
    @Override
    public Result<?> articleList(Integer id, Integer pageNum, Integer pageSize) {
        // 1. 定义查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

        // 2. 顺序排序
        wrapper.orderByAsc(Article::getId);

        // 3. 根据id查询文章表关联的
        wrapper.eq(Article::getCategoryid, id);

        // 4. 封装分页对象
        Page<Article> page = new Page<>(pageNum, pageSize);

        // 5. 调用Mapper进行分页查询
        IPage<Article> articlePage = articleMapper.selectPage(page, wrapper);
        for (Article record : articlePage.getRecords()) {
            Category category = categoryMapper.selectById(record.getCategoryid());
            record.setCategory(category);
        }
        // 6. 获取分页记录
        List<Article> articleList = BeanCopyUtils.copyBeanList(articlePage.getRecords(), Article.class);

        // 7. 封装分页数据返回给前端
        PageVo pageVo = new PageVo(pageNum, pageSize, articlePage.getTotal(), articleList);

        return Result.success(pageVo); // 返回分页封装的结果
    }

    /**
     * 先获取分类列表，再根据分类id获取文章列表
     * */

    @Override
    public Result<?> listArticle(Integer pageNum, Integer pageSize) {
        // 1. 定义查询条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //2. 顺序排序
        wrapper.orderByAsc(Category::getId);
       //3.遍历分类id去拿到文章列表关联的categoryid在写入
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<Category> categories = page.getRecords();
        for (Category category : categories) {
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getCategoryid, category.getId()).orderByDesc(Article::getStop);
            List<Article> articles = articleMapper.selectList(articleWrapper);
            category.setArticle(articles);
        }
        // 4. 封装分页数据返回给前端
        PageVo pageVo = new PageVo(pageNum, pageSize, page.getTotal(), categories);
        return Result.success(pageVo); // 返回分页封装的结果
    }


}
