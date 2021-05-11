package com.kedacom.device.core.msp.fallback;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.device.core.msp.SystemAuthSdk;
import com.kedacom.device.core.exception.MspRemoteCallException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
@Component
public class SystemAuthSdkFallbackFactory implements FallbackFactory<SystemAuthSdk> {

    private volatile SystemAuthSdk systemAuthSdk;

    @Override
    public SystemAuthSdk create(Throwable cause) {

        if (this.systemAuthSdk == null) {

            synchronized (SystemAuthSdkFallbackFactory.class) {

                if (this.systemAuthSdk == null) {

                    this.systemAuthSdk = new SystemAuthSdk() {
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

        return this.systemAuthSdk;

    }
}
