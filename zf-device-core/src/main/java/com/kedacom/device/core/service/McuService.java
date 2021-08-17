package com.kedacom.device.core.service;

import com.kedacom.BaseResult;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.request.McuAccountDTO;
import com.kedacom.mp.mcu.request.McuConfsDTO;
import com.kedacom.mp.mcu.request.McuTemplatesDTO;

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

    BaseResult templates(McuTemplatesDTO dto);

}
