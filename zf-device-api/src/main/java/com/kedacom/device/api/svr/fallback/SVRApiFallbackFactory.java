package com.kedacom.device.api.svr.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.svr.SvrApi;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.vo.*;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 14:56
 * @description
 */
public class SVRApiFallbackFactory implements FallbackFactory<SvrApi> {
    @Override
    public SvrApi create(Throwable throwable) {
        return new SvrApi() {
            @Override
            public BaseResult<BasePage<SvrEntity>> pageQuery(SvrPageQueryDTO queryDTO) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrEntity> info(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrEntity> save(@Valid SvrEntity entity) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrEntity> update(SvrEntity entity) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult delete(Long[] ids) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrLoginVO> loginById(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult logoutById(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrCapVo> svrCap(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrTimeVo> svrTime(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult searchDev(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<EnChnListVo> enChnList(Integer dbId) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> enChn(EnChnDto enChnDto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<CpResetVo> enCpReset(EnCpResetDto dto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> CpReset(CpResetDto dto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DeChnListVo> deChnList(Integer dbId) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> deChn(DeChnDto dto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DecParamVo> decParam(DecParamDto dto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> enDeParam(EnDecParamDto dto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> ptz(PtzDto dto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> remotePoint(RemotePointDto dto) {
                return  BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<RemoteCfgVo> remoteCfg(Integer dbId) {
                return  BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
