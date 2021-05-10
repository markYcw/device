package com.kedacom.device.api.msp;


import com.kedacom.device.api.msp.fallback.TvPlayFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:52
 */
@FeignClient(contextId = "tvPlayFeign", path = "/api/v1/manage/tvplay", fallbackFactory = TvPlayFeignFallbackFactory.class)
public interface TvPlayFeign {
}
