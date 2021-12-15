package com.kedacom.device.api.mcu.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.mcu.UmsMcuApi;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/23 10:05
 * @description 会议平台 CRUD 熔断
 */
public class UmsMcuApiFallbackFactory implements FallbackFactory<UmsMcuApi> {
    @Override
    public UmsMcuApi create(Throwable throwable) {
        return new UmsMcuApi() {
            @Override
            public BaseResult<BasePage<UmsMcuEntity>> pageQuery(McuPageQueryDTO queryDTO) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsMcuEntity>> all() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsMcuEntity> info(Long id) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsMcuEntity> save(UmsMcuEntity entity) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult update(UmsMcuEntity entity) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult delete(Long[] ids) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
