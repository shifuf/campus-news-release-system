package com.campus.Handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shifuf
 * @date 2024/9/21 下午5:01
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间和更新时间
        this.strictInsertFill(metaObject, "createtime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updatetime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时只填充更新时间和更新者
        this.strictUpdateFill(metaObject, "updatetime", Date.class, new Date());
    }

}
