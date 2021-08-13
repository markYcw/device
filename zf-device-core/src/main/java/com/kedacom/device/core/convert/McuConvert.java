package com.kedacom.device.core.convert;

import com.kedacom.device.meetingPlatform.mcu.request.McuLoginRequest;
import com.kedacom.meeting.mcu.entity.UmsMeetingPlatformEntity;
import org.mapstruct.Mapper;

/**
 * @author hxj
 * @date: 2021/8/13 13:46
 * @description mcu参数转换接口
 */
@Mapper(componentModel = "spring")
public interface McuConvert {

    McuLoginRequest login(UmsMeetingPlatformEntity entity);

}
