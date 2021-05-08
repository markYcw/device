package com.kedacom.device.core.msp.fallback;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.device.core.msp.AuthSdk;
import com.kedacom.device.core.msp.exception.MspRemoteCallException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
@Component
public class AuthSdkFallbackFactory implements FallbackFactory<AuthSdk> {

    private volatile AuthSdk authSdk;

    @Override
    public AuthSdk create(Throwable cause) {

        if (this.authSdk == null) {

            synchronized (AuthSdkFallbackFactory.class) {

                if (this.authSdk == null) {

                    this.authSdk = new AuthSdk() {
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

        return this.authSdk;

    }
}
