package com.campus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by IntelliJ IDEA.
 * 图片映射-让图片能够显示出来
 * @author shifuf
 * @date 2024/9/22 上午11:27
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //图片资源映射　　　　　//其中/images是访问图片资源的前缀，比如要访问test1.png。则全路径为：http://localhost:端口号/images/test1.png
        registry.addResourceHandler("/**")
                .addResourceLocations("file:E:/桌面/campusServer/src//main/resources/static/upload/");//此处为设置服务端存储图片的路径（前段上传到后台的图片保存位置）
    }
}
