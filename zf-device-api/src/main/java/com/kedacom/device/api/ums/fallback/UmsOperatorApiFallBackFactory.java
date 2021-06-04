package com.kedacom.device.api.ums.fallback;

import com.kedacom.BaseResult;
import com.kedacom.device.api.ums.UmsOperatorApi;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/4
 */
public class UmsOperatorApiFallBackFactory implements FallbackFactory<UmsOperatorApi> {

    @Override
    public UmsOperatorApi create(Throwable throwable) {
        return new UmsOperatorApi() {

            @Override
            public BaseResult<UmsScheduleGroupCreateResponseDto> createScheduleGroup(UmsScheduleGroupCreateRequestDto request) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> deleteScheduleGroup(UmsScheduleGroupDeleteRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> addScheduleGroupMember(UmsScheduleGroupAddMembersRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> deleteScheduleGroupMember(UmsScheduleGroupDeleteMembersRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsScheduleGroupQueryResponseDto>> selectScheduleGroupList(UmsScheduleGroupQueryRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> queryScheduleGroupStatus(UmsScheduleGroupQueryStatusRequestVo requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> setScheduleGroupSilence(UmsScheduleGroupSetSilenceRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsScheduleGroupQuerySilenceResponseDto> queryScheduleGroupSilence(UmsScheduleGroupQuerySilenceRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> setScheduleGroupMute(UmsScheduleGroupSetMuteRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsScheduleGroupQueryMuteResponseDto> queryScheduleGroupMute(UmsScheduleGroupQueryMuteRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> controlScheduleGroupPtz(UmsScheduleGroupPtzControlRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsScheduleGroupStartVmpMixResponseDto> startScheduleGroupVmpMix(UmsScheduleGroupStartVmpMixRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> updateScheduleGroupVmpMix(UmsScheduleGroupUpdateVmpMixRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> stopScheduleGroupVmpMix(UmsScheduleGroupStopVmpMixRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsScheduleGroupQueryVmpMixResponseDto> queryScheduleGroupVmpMix(UmsScheduleGroupQueryVmpMixRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> callUpSubDevice(UmsScheduleGroupSubDeviceCallUpRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> setScheduleGroupBroadcast(UmsScheduleGroupSetBroadcastRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> cancelScheduleGroupBroadcast(UmsScheduleGroupCancelBroadcastRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsScheduleGroupQueryBroadcastResponseDto> queryScheduleGroupBroadcast(UmsScheduleGroupQueryBroadcastRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> setScheduleGroupMedia(UmsScheduleGroupSetMediaRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<UmsScheduleGroupQueryMediaResponseDto> queryScheduleGroupMedia(UmsScheduleGroupQueryMediaRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<String>> joinScheduleGroupDiscussionGroup(UmsScheduleGroupJoinDiscussionGroupRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> quitScheduleGroupDiscussionGroup(UmsScheduleGroupQuitDiscussionGroupRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<UmsScheduleGroupQueryDiscussionGroupResponseDto>> queryScheduleGroupDiscussionGroup(UmsScheduleGroupQueryDiscussionGroupRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> clearScheduleGroupDiscussionGroup(UmsScheduleGroupClearDiscussionGroupRequestDto requestDto) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
