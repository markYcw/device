package com.kedacom.device.core.remoteSdk.msp.fallback;


import com.kedacom.avIntegration.request.decoder.OsdConfigRequest;
import com.kedacom.avIntegration.request.decoder.OsdDeleteRequest;
import com.kedacom.avIntegration.request.decoder.StyleConfigRequest;
import com.kedacom.avIntegration.request.decoder.StyleQueryRequest;
import com.kedacom.acl.network.data.avIntegration.decoder.OsdConfigResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.OsdDeleteResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.StyleConfigResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.StyleQueryResponse;
import com.kedacom.device.core.exception.MspRemoteCallException;
import com.kedacom.device.core.remoteSdk.msp.DecoderManageSdk;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author wangxy
 * @describe
 * @date 2021/4/1
 */
@Component
public class DecoderManageSdkFallbackFactory implements FallbackFactory<DecoderManageSdk> {

    private volatile DecoderManageSdk decoderManageSdk;

    @Override
    public DecoderManageSdk create(Throwable cause) {

        if (this.decoderManageSdk == null) {

            synchronized (DecoderManageSdkFallbackFactory.class) {

                if (this.decoderManageSdk == null) {

                    this.decoderManageSdk = new DecoderManageSdk() {
                        @Override
                        public OsdConfigResponse osdConfig(OsdConfigRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public OsdDeleteResponse osdDelete(OsdDeleteRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public StyleQueryResponse styleQuery(StyleQueryRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public StyleConfigResponse styleConfig(StyleConfigRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }
                    };
                }
            }
        }

        return this.decoderManageSdk;
    }
}
