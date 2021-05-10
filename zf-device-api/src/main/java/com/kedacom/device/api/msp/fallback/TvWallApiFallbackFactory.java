package com.kedacom.device.api.msp.fallback;

import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.avIntegration.response.tvwall.*;
import com.kedacom.device.api.msp.TvWallApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 18:58
 */
public class TvWallApiFallbackFactory implements FallbackFactory<TvWallApi> {

    private TvWallApi tvWallApi;

    @Override
    public TvWallApi create(Throwable cause) {
      return new TvWallApi() {
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
