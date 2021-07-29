package com.kedacom.device.core.remoteSdk.mt.fallback;

import com.kedacom.device.core.remoteSdk.mt.MtFiveSdk;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author hxj
 * @Date: 2021/7/29 11:16
 * @Description 终端5.0中间件接口熔断
 */
@Component
public class MtFiveFallbackFactory implements FallbackFactory<MtFiveSdk> {

    @Override
    public MtFiveSdk create(Throwable cause) {
        return new MtFiveSdk() {
        };
    }
}
