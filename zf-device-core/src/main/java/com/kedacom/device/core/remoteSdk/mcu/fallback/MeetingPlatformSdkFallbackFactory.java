package com.kedacom.device.core.remoteSdk.mcu.fallback;

import com.kedacom.device.core.remoteSdk.mcu.MeetingPlatformSdk;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author hxj
 * @Date: 2021/8/12 13:32
 * @Description 中间件会议平台sdk接口熔断类
 */
@Component
public class MeetingPlatformSdkFallbackFactory implements FallbackFactory<MeetingPlatformSdk> {
    @Override
    public MeetingPlatformSdk create(Throwable cause) {
        return null;
    }
}
