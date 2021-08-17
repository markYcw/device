package com.kedacom.device.core.convert;

import com.kedacom.device.mp.mcu.request.McuAccountRequest;
import com.kedacom.device.mp.mcu.request.McuConfsRequest;
import com.kedacom.device.mp.mcu.request.McuLoginRequest;
import com.kedacom.device.mp.mcu.request.McuTemplatesRequest;
import com.kedacom.device.mp.mcu.response.McuConfsResponse;
import com.kedacom.device.mp.mcu.response.McuTemplatesResponse;
import com.kedacom.mp.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.mp.mcu.request.McuAccountDTO;
import com.kedacom.mp.mcu.request.McuConfsDTO;
import com.kedacom.mp.mcu.request.McuTemplatesDTO;
import com.kedacom.mp.mcu.response.McuConfsVO;
import org.mapstruct.Mapper;

/**
 * @author hxj
 * @date: 2021/8/13 13:46
 * @description mcu参数转换接口
 */
@Mapper(componentModel = "spring")
public interface McuConvert {

    McuLoginRequest login(UmsMeetingPlatformEntity entity);

    McuAccountRequest account(McuAccountDTO dto);

    McuConfsRequest confs(McuConfsDTO dto);

    McuConfsVO accountRes(McuConfsResponse confsResponse);

    McuTemplatesRequest templates(McuTemplatesDTO dto);

    McuConfsVO templatesRes(McuTemplatesResponse response);

}
