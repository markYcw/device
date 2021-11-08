package com.kedacom.device.api.cu.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.CuPageQueryDTO;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.dto.SelectTreeDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.DomainsVo;
import com.kedacom.cu.vo.LocalDomainVo;
import com.kedacom.cu.vo.TimeVo;
import com.kedacom.cu.vo.ViewTreesVo;
import com.kedacom.device.api.cu.CuApi;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/1 9:39
 * @description
 */
public class CuApiFallbackFactory implements FallbackFactory<CuApi> {
    @Override
    public CuApi create(Throwable throwable) {
        return new CuApi() {
            @Override
            public BaseResult<BasePage<CuEntity>> pageQuery(CuPageQueryDTO queryDTO) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<CuEntity> info(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<CuEntity> save(CuEntity entity) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<CuEntity> update(CuEntity entity) {
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
