package com.kedacom.device.api.vs.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.vs.VrsFiveApi;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.*;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/1/10 14:02
 * @description
 */
public class VrsFiveDefaultFallbackFactory implements FallbackFactory<VrsFiveApi> {
    @Override
    public VrsFiveApi create(Throwable throwable) {
        return new VrsFiveApi() {
            @Override
            public BaseResult<List<VrsVo>> vrsFiveList() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<BasePage<VsEntity>> terminalList(VrsQuery vrsQuery) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<VrsVo> saveVrs(VrsVo vrsVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult deleteVrs(List<Integer> ids) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<VrsVo> updateVrs(VrsVo vrsVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<VrsVo> vtsInfo(Integer id) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<VrsRecInfoDecVo> vrsQueryHttpRec(QueryRecListVo queryRecListVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<VrsRecInfoVo> queryRec(QueryRecVo vo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<LiveInfoVo> queryLive(QueryLiveVo vo) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
