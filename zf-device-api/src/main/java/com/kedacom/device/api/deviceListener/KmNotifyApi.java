package com.kedacom.device.api.deviceListener;

import com.kedacom.BaseResult;
import com.kedacom.device.api.deviceListener.fallback.KmNotifyDefaultFallbackFactory;
import com.kedacom.deviceListener.RegisterListenerVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * KM设备订阅通知
 */
@FeignClient(value = "device-server", path = "/api-device/listener", contextId = "kmNotifyApi", fallbackFactory = KmNotifyDefaultFallbackFactory.class)
public interface KmNotifyApi {

    @ApiOperation("注册订阅设备消息")
    @PostMapping("/registerListener")
    BaseResult<RegisterListenerVo> registerListener(@RequestBody RegisterListenerVo registerUrlVo);

    @ApiOperation("取消订阅设备消息")
    @PostMapping("/UnRegisterListener")
    BaseResult<Boolean> UnRegisterListener(@RequestBody RegisterListenerVo registerUrlVo);
}
