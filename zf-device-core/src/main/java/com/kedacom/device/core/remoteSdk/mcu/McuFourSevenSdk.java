package com.kedacom.device.core.remoteSdk.mcu;

import com.kedacom.device.core.config.RemoteFeignConfig;
import com.kedacom.device.core.remoteSdk.mcu.fallback.McuFourSevenFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author hxj
 * @Date: 2021/7/29 11:21
 * @Description 会议平台4.7中间件接口
 */
@FeignClient(name = "mcu",
        contextId = "mcuFourSeven",
        url = "${zf.km.server_addr}",
        path = "/services/v1/mcu4.7",
        fallbackFactory = McuFourSevenFallbackFactory.class,
        configuration = RemoteFeignConfig.class)
public interface McuFourSevenSdk {
}
