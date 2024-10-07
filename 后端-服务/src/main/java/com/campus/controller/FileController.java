package com.campus.controller;

import com.campus.util.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by IntelliJ IDEA.
 * 文件上传
 * @author shifuf
 * @date 2024/9/22 上午11:10
 */

@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 文件回调显示地址
     */
    private static final String SHOW_DIR = "http://localhost:8080/images/";
    /**
     * 文件物理存储位置
     */
    private static final String STORE_DIR = "E:\\桌面\\campusServer\\src\\main\\resources\\static\\upload\\images\\";

    /**
     * 图片文件上传
     * */
    @RequestMapping("/upload")
    public Result<?> upload(MultipartFile file){
        //上传校验
        if (file.isEmpty()){
            return Result.failed("文件为空");
        }

        //重命名文件
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = System.currentTimeMillis() + suffixName;
        //文件存储路径
        String filePath = STORE_DIR + newFileName;
        //文件上传
        try {
            file.transferTo(new java.io.File(filePath));
            return Result.success(SHOW_DIR+newFileName , "文件上传成功");
        } catch (Exception e) {
            return Result.failed(e,"文件上传失败");
        }

    }

}
