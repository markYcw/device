package com.kedacom.device.api.msp;

import com.kedacom.device.api.msp.fallback.SchemeManageApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
@FeignClient(contextId = "schemeManageApi", path = "/api/v1/manage/scheme", fallbackFactory = SchemeManageApiFallbackFactory.class)
public interface SchemeManageApi {
}
