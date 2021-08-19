package com.kedacom.device.api.mcu.fallback;

import com.kedacom.device.api.mcu.McuApi;
import feign.hystrix.FallbackFactory;

/**
 * @Author hxj
 * @Date: 2021/8/12 13:41
 * @Description 会议平台api熔断类
 */
public class McuApiFallbackFactory implements FallbackFactory<McuApi> {
    @Override
    public McuApi create(Throwable cause) {
        return null;
    }
}
