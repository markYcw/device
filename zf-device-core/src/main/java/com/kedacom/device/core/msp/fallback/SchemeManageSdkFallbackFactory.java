package com.kedacom.device.core.msp.fallback;


import com.kedacom.device.core.msp.SchemeManageSdk;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
@Component
public class SchemeManageSdkFallbackFactory implements FallbackFactory<SchemeManageSdk> {
    @Override
    public SchemeManageSdk create(Throwable throwable) {
        return null;
    }
}
