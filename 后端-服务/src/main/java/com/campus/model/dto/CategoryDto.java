package com.campus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午4:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    //id
    private Long id;
    //分类名称
    private String name;
}
