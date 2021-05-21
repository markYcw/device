package com.kedacom.device.api.ums.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.ums.UmsManageApi;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/21
 */
public class UmsManageApiFallBackFactory implements FallbackFactory<UmsManageApi> {

    @Override
    public UmsManageApi create(Throwable throwable) {
        return new UmsManageApi() {

            @Override
            public BaseResult<String> insertUmsDevice(UmsDeviceInfoAddRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> updateUmsDevice(UmsDeviceInfoUpdateRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> deleteUmsDevice(UmsDeviceInfoDeleteRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> syncDeviceData(UmsDeviceInfoSyncRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> getUmsLastSyncTime(UmsDeviceInfoSyncRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<BasePage<UmsDeviceInfoSelectResponseDto>> selectUmsDeviceList(UmsDeviceInfoSelectRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<BasePage<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceList(UmsSubDeviceInfoQueryRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGroupId(UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByIds(UmsSubDeviceInfoQueryByIdsRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGbIds(UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> deleteUmsSubDevice(UmsSubDeviceInfoDeleteRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsAlarmTypeQueryResponseDto>> selectUmsAlarmTypeList() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupList(UmsScheduleGroupQueryRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<SelectChildUmsGroupResponseDto>> selectChildUmsGroupList(SelectChildUmsGroupRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupItemList(UmsScheduleGroupItemQueryRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }

}
