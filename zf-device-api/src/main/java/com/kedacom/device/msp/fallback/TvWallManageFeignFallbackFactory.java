package com.kedacom.device.msp.fallback;

import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.avIntegration.response.tvwall.*;
import com.kedacom.device.msp.TvWallManageFeign;
import com.kedacom.device.msp.exception.MspRemoteCallException;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 18:58
 */
public class TvWallManageFeignFallbackFactory implements FallbackFactory<TvWallManageFeign> {
    
    private TvWallManageFeign tvWallManageFeign;
    
    @Override
    public TvWallManageFeign create(Throwable cause) {
        if (this.tvWallManageFeign == null) {

            synchronized (TvWallManageFeignFallbackFactory.class) {

                if (this.tvWallManageFeign == null) {

                    this.tvWallManageFeign = new TvWallManageFeign() {
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

        return this.tvWallManageFeign;
    }
}
