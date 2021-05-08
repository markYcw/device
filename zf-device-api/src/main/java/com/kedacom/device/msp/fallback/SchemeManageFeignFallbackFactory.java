package com.kedacom.device.msp.fallback;

import com.kedacom.device.msp.SchemeManageFeign;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
public class SchemeManageFeignFallbackFactory implements FallbackFactory<SchemeManageFeign> {
    @Override
    public SchemeManageFeign create(Throwable throwable) {
        return null;
    }
}
