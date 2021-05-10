package com.kedacom.device.api.streammedia;

import com.kedacom.device.api.streammedia.fallback.StreamMediaApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 10:19
 */
@FeignClient(contextId = "streamMediaApi", path = "device/streamMedia", fallbackFactory = StreamMediaApiFallbackFactory.class)
public interface StreamMediaApi {
}
