package com.kedacom.device.core.remoteSdk.mcu.fallback;

import com.kedacom.device.core.remoteSdk.mcu.McuFourSevenSdk;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author hxj
 * @Date: 2021/7/29 11:22
 * @Description 会议平台4.7中间件接口熔断
 */
@Component
public class McuFourSevenFallbackFactory implements FallbackFactory<McuFourSevenSdk> {
    @Override
    public McuFourSevenSdk create(Throwable cause) {
        return new McuFourSevenSdk() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }
}
