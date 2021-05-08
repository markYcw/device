package com.kedacom.device.msp.fallback;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.device.msp.AuthFeign;
import com.kedacom.device.msp.exception.MspRemoteCallException;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
public class AuthFeignFallbackFactory implements FallbackFactory<AuthFeign> {

    private volatile AuthFeign authFeign;

    @Override
    public AuthFeign create(Throwable cause) {

        if (this.authFeign == null) {

            synchronized (AuthFeignFallbackFactory.class) {

                if (this.authFeign == null) {

                    this.authFeign = new AuthFeign() {
                        @Override
                        public SystemLoginResponse login(SystemLoginRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public SystemKeepAliveResponse keepAlive(RequestBaseParam request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public SystemVersionResponse version(RequestBaseParam request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public SystemLogOutResponse logout(RequestBaseParam request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }
                    };
                }
            }
        }

        return this.authFeign;

    }
}
