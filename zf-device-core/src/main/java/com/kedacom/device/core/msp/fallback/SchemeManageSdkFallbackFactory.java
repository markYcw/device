package com.kedacom.device.core.msp.fallback;


import com.kedacom.device.core.msp.SchemeManageSdk;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
public class SchemeManageSdkFallbackFactory implements FallbackFactory<SchemeManageSdk> {
    @Override
    public SchemeManageSdk create(Throwable throwable) {
        return null;
    }
}
