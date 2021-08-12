package com.kedacom.device.api.mcu.fallback;

import com.kedacom.device.api.mcu.MeetingPlatformApi;
import feign.hystrix.FallbackFactory;

/**
 * @Author hxj
 * @Date: 2021/8/12 13:41
 * @Description 会议平台api熔断类
 */
public class MeetingPlatformApiFallbackFactory implements FallbackFactory<MeetingPlatformApi> {
    @Override
    public MeetingPlatformApi create(Throwable cause) {
        return null;
    }
}
