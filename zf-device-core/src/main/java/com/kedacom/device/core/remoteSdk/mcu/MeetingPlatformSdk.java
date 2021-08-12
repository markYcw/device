package com.kedacom.device.core.remoteSdk.mcu;

import com.kedacom.device.core.config.RemoteFeignConfig;
import com.kedacom.device.core.remoteSdk.mcu.fallback.MeetingPlatformSdkFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author hxj
 * @Date: 2021/7/29 11:21
 * @Description 会议平台和中间件交互接口
 */
@FeignClient(name = "mcu",
        contextId = "meetingPlatform",
        url = "${zf.kmProxy.server_addr}",
        path = "/services/v1",
        fallbackFactory = MeetingPlatformSdkFallbackFactory.class,
        configuration = RemoteFeignConfig.class)
public interface MeetingPlatformSdk {
}
