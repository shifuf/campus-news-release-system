package com.campus.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * 用户分页视图对象
 * @author shifuf
 * @date 2024/9/21 上午9:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页显示的条数
     */
    private Integer pageSize;
    /**
     * 总条数
     * */
    private Long total;
    /**
     * 数据
     */
    private List<?> rows;
}
