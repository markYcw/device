package com.kedacom.device.core.remoteSdk.msp;

import com.kedacom.device.core.remoteSdk.msp.fallback.SubscribeSdkFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 10:09
 */
@FeignClient(name = "msp",
        contextId = "msp-subscribe",
        url = "${zf.msp.server_addr}",
        path = "/api/v1/manage/subscribe",
        fallbackFactory = SubscribeSdkFallbackFactory.class)
public interface SubscribeSdk {


}
