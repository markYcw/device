package com.kedacom.device.api.msp.fallback;

import com.kedacom.BaseResult;
import com.kedacom.msp.request.tvwall.*;
import com.kedacom.msp.response.tvwall.*;
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
            public BaseResult<TvWallListVO> ls(TvWallListRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<TvWallLayoutVO> layout(TvWallLayoutRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<TvWallQueryPipelineVO> query(TvWallQueryPipelineRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<TvWallConfigVO> config(TvWallConfigRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<TvWallPipelineBindVO> configBind(TvWallPipelineBindRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult delete(TvWallDeleteRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }
        };
    }
}
