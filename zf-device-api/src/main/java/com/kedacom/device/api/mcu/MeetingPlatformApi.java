package com.kedacom.device.api.mcu;

import com.kedacom.device.api.mcu.fallback.MeetingPlatformApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author hxj
 * @Date: 2021/8/12 13:37
 * @Description 会议平台qpi
 */
@FeignClient(name = "device-server",contextId = "meetingPlatformApi",path = "/api-device/ums/mcu",fallbackFactory = MeetingPlatformApiFallbackFactory.class)
public interface MeetingPlatformApi {
}
