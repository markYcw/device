package com.kedacom.device.api.msp.fallback;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.device.api.msp.SystemAuthApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
public class SystemAuthApiFallbackFactory implements FallbackFactory<SystemAuthApi> {

    private volatile SystemAuthApi systemAuthApi;

    @Override
    public SystemAuthApi create(Throwable cause) {
        return new SystemAuthApi() {
            @Override
            public SystemLoginResponse login(SystemLoginRequest request) {
                return null;
            }

            @Override
            public SystemKeepAliveResponse keepAlive(RequestBaseParam request) {
                return null;
            }

            @Override
            public SystemVersionResponse version(RequestBaseParam request) {
                return null;
            }

            @Override
            public SystemLogOutResponse logout(RequestBaseParam request) {
                return null;
            }
        };
    }
}
