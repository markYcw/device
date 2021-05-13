package com.kedacom.device.core.msp.fallback;


import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeConfigResponse;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeQueryResponse;
import com.kedacom.device.core.exception.MspRemoteCallException;
import com.kedacom.device.core.msp.SchemeManageSdk;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
@Component
public class SchemeManageSdkFallbackFactory implements FallbackFactory<SchemeManageSdk> {

    private volatile SchemeManageSdk schemeManageSdk;

    @Override
    public SchemeManageSdk create(Throwable cause) {

        if (this.schemeManageSdk == null) {

            synchronized (SchemeManageSdkFallbackFactory.class) {

                if (this.schemeManageSdk == null) {

                    this.schemeManageSdk = new SchemeManageSdk() {
                        @Override
                        public SchemeConfigResponse config(SchemeConfigRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public SchemeQueryResponse query(SchemeQueryRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }
                    };
                }
            }
        }

        return this.schemeManageSdk;
    }
}
