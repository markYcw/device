package com.kedacom.device.core.remoteSdk.mt;

import com.kedacom.device.core.config.RemoteFeignConfig;
import com.kedacom.device.core.remoteSdk.mt.fallback.MtThreeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author hxj
 * @Date: 2021/7/29 11:19
 * @Description 终端3.0中间件接口
 */
@FeignClient(name = "mt",
        contextId = "mtThree",
        url = "${zf.km.server_addr}",
        path = "/services/v1/mt3.0",
        fallbackFactory = MtThreeFallbackFactory.class,
        configuration = RemoteFeignConfig.class)
public interface MtThreeSdk {
}
