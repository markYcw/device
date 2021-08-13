package com.kedacom.device.core.service;

import com.kedacom.BaseResult;
import com.kedacom.meeting.mcu.McuRequestDTO;

/**
 * @author hxj
 * @date: 2021/8/13 13:50
 * @description 会议平台业务接口
 */
public interface McuService {

    BaseResult login(McuRequestDTO entity);

}
