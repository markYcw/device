package com.kedacom.device.api.msp.fallback;

import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.avIntegration.response.tvwall.*;
import com.kedacom.device.api.msp.TvWallFeign;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 18:58
 */
public class TvWallFeignFallbackFactory implements FallbackFactory<TvWallFeign> {

    private TvWallFeign tvWallFeign;

    @Override
    public TvWallFeign create(Throwable cause) {
      return new TvWallFeign() {
          @Override
          public TvWallListResponse ls(TvWallListRequest request) {
              return null;
          }

          @Override
          public TvWallLayoutResponse layout(TvWallLayoutRequest request) {
              return null;
          }

          @Override
          public TvWallQueryPipelineResponse query(TvWallQueryPipelineRequest request) {
              return null;
          }

          @Override
          public TvWallConfigResponse config(TvWallConfigRequest request) {
              return null;
          }

          @Override
          public TvWallPipelineBindResponse configBind(TvWallPipelineBindRequest request) {
              return null;
          }

          @Override
          public TvWallDeleteResponse delete(TvWallDeleteRequest request) {
              return null;
          }
      };
    }
}
