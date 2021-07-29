package com.kedacom.device.core.remoteSdk.mt;

import com.kedacom.device.core.config.RemoteFeignConfig;
import com.kedacom.device.core.remoteSdk.mt.fallback.MtFiveFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author hxj
 * @Date: 2021/7/29 11:00
 * @Description 终端5.0中间件接口
 */
@FeignClient(name = "mt",
        contextId = "mtFive",
        url = "${zf.km.server_addr}",
        path = "/services/v1/mt5.0",
        fallbackFactory = MtFiveFallbackFactory.class,
        configuration = RemoteFeignConfig.class)
public interface MtFiveSdk {
}
