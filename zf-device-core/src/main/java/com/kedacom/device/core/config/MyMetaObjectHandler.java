package com.kedacom.device.core.config;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", Date.class, DateUtil.now());
        this.strictInsertFill(metaObject, "modifyTime", Date.class, DateUtil.now());
        this.strictInsertFill(metaObject, "updateTime", Date.class, DateUtil.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "modifyTime", Date.class, DateUtil.now());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtil.now());
    }

}