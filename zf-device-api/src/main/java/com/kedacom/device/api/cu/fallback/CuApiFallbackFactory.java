package com.kedacom.device.api.cu.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.DevEntityQuery;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.dto.SelectTreeDto;
import com.kedacom.cu.vo.*;
import com.kedacom.device.api.cu.DevApi;
import feign.hystrix.FallbackFactory;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/1 9:39
 * @description
 */
public class CuApiFallbackFactory implements FallbackFactory<DevApi> {
    @Override
    public DevApi create(Throwable throwable) {
        return new DevApi() {

            @Override
            public BaseResult<BasePage<DevEntityVo>> list(DevEntityQuery queryDTO) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DevEntityVo> info(Integer kmId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DevEntityVo> saveDevFeign(DevEntityVo devEntityVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DevEntityVo> updateDev(DevEntityVo devEntityVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult delete(Long[] ids) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> loginById(CuRequestDto dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> logoutById(CuRequestDto dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<LocalDomainVo> localDomain(CuRequestDto dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DomainsVo> domains(CuRequestDto dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<TimeVo> time(CuRequestDto dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<ViewTreesVo> viewTrees(CuRequestDto dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> selectTree(SelectTreeDto dto) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
