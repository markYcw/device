package com.kedacom.device.core.service;

import com.kedacom.BaseResult;
import com.kedacom.meeting.mcu.McuRequestDTO;
import com.kedacom.meeting.mcu.request.McuAccountDTO;
import com.kedacom.meeting.mcu.request.McuConfsDTO;

/**
 * @author hxj
 * @date: 2021/8/13 13:50
 * @description 会议平台业务接口
 */
public interface McuService {

    BaseResult login(McuRequestDTO dto);

    BaseResult logout(McuRequestDTO dto);

    BaseResult account(McuAccountDTO dto);

    BaseResult confs(McuConfsDTO dto);

}
