package com.kedacom.device.api.msp;

import com.kedacom.device.api.msp.fallback.SchemeManageFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
@FeignClient(contextId = "schemeManageFeign", path = "/api/v1/manage/scheme", fallbackFactory = SchemeManageFeignFallbackFactory.class)
public interface SchemeManageFeign {
}
