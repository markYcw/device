package com.kedacom.device.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.mp.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;

/**
 * 会议平台
 *
 * @author hxj
 * @email hexijian@kedacom.com
 * @date 2021-08-12 10:19:30
 */
public interface UmsMeetingPlatformService extends IService<UmsMeetingPlatformEntity> {

    BaseResult<BasePage<UmsMeetingPlatformEntity>> pageQuery(McuPageQueryDTO queryDTO);

}

