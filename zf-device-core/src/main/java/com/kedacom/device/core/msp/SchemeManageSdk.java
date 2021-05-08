package com.kedacom.device.core.msp;

import com.kedacom.device.core.msp.fallback.SchemeManageSdkFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
@FeignClient(name = "msp",
        contextId = "msp-scheme",
        url = "${zf.msp.server_addr}",
        path = "/api/v1/manage/scheme",
        fallbackFactory = SchemeManageSdkFallbackFactory.class)
public interface SchemeManageSdk {
}
