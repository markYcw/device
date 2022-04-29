package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.data.entity.DataEntity;
import com.kedacom.svr.dto.FindByIpOrNameDto;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.*;

import java.util.List;

/**
 * 数据迁移
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-04 17:09:24
 */
public interface DataService extends IService<DataEntity> {
    /**
     * 数据迁移结果查询
     * @return
     */
    BaseResult<Integer> data();

    /**
     * 第一次数据迁移cu svr mt vrs2100/4100 mcu
     */
    void dcOne();


}
