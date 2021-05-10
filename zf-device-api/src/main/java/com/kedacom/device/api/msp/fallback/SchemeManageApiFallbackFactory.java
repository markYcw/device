package com.kedacom.device.api.msp.fallback;

import com.kedacom.device.api.msp.SchemeManageApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
public class SchemeManageApiFallbackFactory implements FallbackFactory<SchemeManageApi> {
    @Override
    public SchemeManageApi create(Throwable throwable) {
        return null;
    }
}
