package com.kedacom.device.msp.fallback;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.SystemLoginRequest;
import com.kedacom.avIntegration.response.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.SystemLogOutResponse;
import com.kedacom.avIntegration.response.SystemLoginResponse;
import com.kedacom.avIntegration.response.SystemVersionResponse;
import com.kedacom.device.msp.AuthFeign;
import com.kedacom.device.msp.exception.MspRemoteCallException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
@Component
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
