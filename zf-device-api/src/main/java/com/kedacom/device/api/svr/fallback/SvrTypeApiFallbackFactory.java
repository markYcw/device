package com.kedacom.device.api.svr.fallback;


import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.svr.SvrTypeApi;
import com.kedacom.svr.entity.SvrTypeEntity;
import com.kedacom.svr.pojo.SvrTypePageQueryDTO;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 14:56
 * @description
 */
public class SvrTypeApiFallbackFactory implements FallbackFactory<SvrTypeApi> {

    @Override
    public SvrTypeApi create(Throwable throwable) {
        return new SvrTypeApi() {
            @Override
            public BaseResult<BasePage<SvrTypeEntity>> pageQuery(SvrTypePageQueryDTO queryDTO) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<SvrTypeEntity>> all() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrTypeEntity> info(Integer dbId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrTypeEntity> save(SvrTypeEntity entity) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<SvrTypeEntity> update(SvrTypeEntity entity) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult delete(Long[] ids) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
