package com.kedacom.device.api.nm.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.nm.NmApi;
import com.kedacom.newMedia.dto.SVROrderDTO;
import com.kedacom.streamMedia.request.GetAudioCapDTO;
import com.kedacom.streamMedia.request.GetBurnStateDTO;
import com.kedacom.streamMedia.request.GetSvrAudioActStateDTO;
import com.kedacom.streamMedia.response.GetAudioCapVO;
import com.kedacom.streamMedia.response.GetBurnStateVO;
import com.kedacom.streamMedia.response.GetSvrAudioActStateVo;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/6/6 11:22
 * @description
 */
public class NmApiFallbackFactory implements FallbackFactory<NmApi> {
    @Override
    public NmApi create(Throwable throwable) {
        return new NmApi() {
            @Override
            public BaseResult<String> insertUmsDevice(UmsDeviceInfoAddRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> syncDeviceData(UmsDeviceInfoSyncRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> updateUmsDevice(UmsDeviceInfoUpdateRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<String> deleteUmsDevice(UmsDeviceInfoDeleteRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<BasePage<UmsDeviceInfoSelectResponseDto>> selectUmsDeviceList(UmsDeviceInfoSelectRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsDeviceInfoSelectByIdResponseDto> getDeviceInfoById(UmsDeviceInfoSelectByIdRequestDto requestDto) {
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
            public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGbIds(UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto) {
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
            public BaseResult<Boolean> sendOrderData(SVROrderDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<GetAudioCapVO> getAudioCap(GetAudioCapDTO getAudioCapDTO) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<GetSvrAudioActStateVo> getSvrAudioActState(GetSvrAudioActStateDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<GetBurnStateVO> getBurnState(GetBurnStateDTO getBurnStateDTO) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
