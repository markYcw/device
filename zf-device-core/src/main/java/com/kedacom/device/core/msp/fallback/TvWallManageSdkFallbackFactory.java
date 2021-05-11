package com.kedacom.device.core.msp.fallback;

import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.avIntegration.response.tvwall.*;
import com.kedacom.device.core.msp.TvWallManageSdk;
import com.kedacom.device.core.exception.MspRemoteCallException;
import feign.hystrix.FallbackFactory;


/**
 * @Auther: hxj
 * @Date: 2021/5/6 18:58
 */
public class TvWallManageSdkFallbackFactory implements FallbackFactory<TvWallManageSdk> {
    
    private TvWallManageSdk tvWallManageSdk;
    
    @Override
    public TvWallManageSdk create(Throwable cause) {
        if (this.tvWallManageSdk == null) {

            synchronized (TvWallManageSdkFallbackFactory.class) {

                if (this.tvWallManageSdk == null) {

                    this.tvWallManageSdk = new TvWallManageSdk() {
                        @Override
                        public TvWallListResponse ls(TvWallListRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvWallLayoutResponse layout(TvWallLayoutRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvWallQueryPipelineResponse query(TvWallQueryPipelineRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvWallConfigResponse config(TvWallConfigRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvWallPipelineBindResponse configBind(TvWallPipelineBindRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvWallDeleteResponse delete(TvWallDeleteRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }
                    };
                }
            }
        }

        return this.tvWallManageSdk;
    }
}
