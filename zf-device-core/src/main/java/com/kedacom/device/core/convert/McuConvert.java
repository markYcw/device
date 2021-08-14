package com.kedacom.device.core.convert;

import com.kedacom.device.meetingPlatform.mcu.request.McuAccountRequest;
import com.kedacom.device.meetingPlatform.mcu.request.McuConfsRequest;
import com.kedacom.device.meetingPlatform.mcu.request.McuLoginRequest;
import com.kedacom.device.meetingPlatform.mcu.response.McuConfsResponse;
import com.kedacom.meeting.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.meeting.mcu.request.McuAccountDTO;
import com.kedacom.meeting.mcu.request.McuConfsDTO;
import com.kedacom.meeting.mcu.response.McuConfsVO;
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

}
