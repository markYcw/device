package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.svr.entity.SvrTypeEntity;
import com.kedacom.svr.pojo.SvrTypePageQueryDTO;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/1/6 10:20
 * @description
 */
public interface SvrTypeService extends IService<SvrTypeEntity> {

    BaseResult<BasePage<SvrTypeEntity>> pageQuery(SvrTypePageQueryDTO queryDTO);

    BaseResult<SvrTypeEntity> saveInfo(SvrTypeEntity entity);
}
