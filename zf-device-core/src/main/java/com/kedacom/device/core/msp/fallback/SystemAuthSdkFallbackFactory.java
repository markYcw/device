package com.kedacom.device.core.msp.fallback;

import com.alibaba.fastjson.JSON;
import com.kedacom.acl.network.data.avIntegration.auth.SystemKeepAliveResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLogOutResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLoginResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemVersionResponse;
import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.device.core.exception.MspRemoteCallException;
import com.kedacom.device.core.msp.SystemAuthSdk;
import com.kedacom.device.core.msp.entity.SystemLoginDTO;
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
                        public SystemLoginResponse login(SystemLoginDTO request) {
                            throw new MspRemoteCallException(JSON.toJSONString(cause));
                        }

                        @Override
                        public SystemKeepAliveResponse keepAlive(RequestBaseParam request) {
                            throw new MspRemoteCallException(JSON.toJSONString(cause));
                        }

                        @Override
                        public SystemVersionResponse version(RequestBaseParam request) {
                            throw new MspRemoteCallException(JSON.toJSONString(cause));
                        }

                        @Override
                        public SystemLogOutResponse logout(RequestBaseParam request) {
                            throw new MspRemoteCallException(JSON.toJSONString(cause));
                        }
                    };
                }
            }
        }

        return this.systemAuthSdk;

    }
}
