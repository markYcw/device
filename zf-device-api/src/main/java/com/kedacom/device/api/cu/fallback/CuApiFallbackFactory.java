package com.kedacom.device.api.cu.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.*;
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
            public BaseResult<DevEntityVo> loginById(CuRequestDto dto) {
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
            public BaseResult<Long> getTime(Integer kmId) {
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

            @Override
            public BaseResult<String> hb(Integer kmId) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> controlPtz(ControlPtzRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> startRec(PlatRecStartVo platRecStartVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> stopRec(PlatRecStopVo platRecStopVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> startPuRec(PuRecStartVo puRecStartVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> stopPuRec(PuRecStopVo puRecStopVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> openLockingRec(OpenLockingRecRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> cancelLockingRec(CancelLockingRecRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<QueryVideoResponseVo> queryVideo(QueryVideoRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<QueryVideoCalendarResponseVo> queryVideoCalendar(QueryVideoCalendarRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DiskInfoVo> queryDisk(QueryDiskRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<DevEntityVo> findByCondition(FindCuByConditionVo findCuByConditionVo) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
