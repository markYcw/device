package com.kedacom.device.core.remoteSdk.mt.fallback;

import com.kedacom.device.core.remoteSdk.mt.MtThreeSdk;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author hxj
 * @Date: 2021/7/29 11:20
 * @Description 终端3.0中间件接口熔断
 */
@Component
public class MtThreeFallbackFactory implements FallbackFactory<MtThreeSdk> {
    @Override
    public MtThreeSdk create(Throwable cause) {
        return new MtThreeSdk() {

        };
    }
}
