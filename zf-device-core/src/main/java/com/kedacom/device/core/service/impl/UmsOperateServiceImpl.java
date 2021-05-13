package com.kedacom.device.core.service.impl;

import com.kedacom.device.core.service.UmsOperateService;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Slf4j
@Service
public class UmsOperateServiceImpl implements UmsOperateService {


    @Override
    public UmsScheduleGroupCreateResponseDto createScheduleGroup(UmsScheduleGroupCreateRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean deleteScheduleGroup(UmsScheduleGroupDeleteRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean addScheduleGroupMember(UmsScheduleGroupAddMembersRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean deleteScheduleGroupMember(UmsScheduleGroupDeleteMembersRequestDto requestDto) {
        return null;
    }

    @Override
    public List<UmsScheduleGroupQueryResponseDto> selectScheduleGroupList(UmsScheduleGroupQueryRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean queryScheduleGroupStatus(UmsScheduleGroupQueryStatusRequestVo requestDto) {
        return null;
    }

    @Override
    public Boolean setScheduleGroupSilence(UmsScheduleGroupSetSilenceRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsScheduleGroupQuerySilenceResponseDto queryScheduleGroupSilence(UmsScheduleGroupQuerySilenceRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean setScheduleGroupMute(UmsScheduleGroupSetMuteRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsScheduleGroupQueryMuteResponseDto queryScheduleGroupMute(UmsScheduleGroupQueryMuteRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean controlScheduleGroupPtz(UmsScheduleGroupPtzControlRequestDto requestDto) {
        return null;
    }

    @Override
    public List<UmsScheduleGroupJoinDiscussionGroupResponseDto> joinScheduleGroupDiscussionGroup(UmsScheduleGroupJoinDiscussionGroupRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean quitScheduleGroupDiscussionGroup(UmsScheduleGroupQuitDiscussionGroupRequestDto requestDto) {
        return null;
    }

    @Override
    public List<UmsScheduleGroupQueryDiscussionGroupResponseDto> queryScheduleGroupDiscussionGroup(UmsScheduleGroupQueryDiscussionGroupRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean clearScheduleGroupDiscussionGroup(UmsScheduleGroupClearDiscussionGroupRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsScheduleGroupStartVmpMixResponseDto startScheduleGroupVmpMix(UmsScheduleGroupStartVmpMixRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsScheduleGroupUpdateVmpMixResponseDto updateScheduleGroupVmpMix(UmsScheduleGroupUpdateVmpMixRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean stopScheduleGroupVmpMix(UmsScheduleGroupStopVmpMixRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsScheduleGroupQueryVmpMixResponseDto queryScheduleGroupVmpMix(UmsScheduleGroupQueryVmpMixRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsStartRecResponseDto startRec(UmsStartRecRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean stopRec(UmsStopRecRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsQueryRecListResponseDto queryRecList(UmsQueryRecListRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean callUpSubDevice(UmsScheduleGroupSubDeviceCallUpRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean setScheduleGroupBroadcast(UmsScheduleGroupSetBroadcastRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean cancelScheduleGroupBroadcast(UmsScheduleGroupCancelBroadcastRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsScheduleGroupQueryBroadcastResponseDto cancelScheduleGroupBroadcast(UmsScheduleGroupQueryBroadcastRequestDto requestDto) {
        return null;
    }

    @Override
    public Boolean setScheduleGroupMedia(UmsScheduleGroupSetMediaRequestDto requestDto) {
        return null;
    }

    @Override
    public UmsScheduleGroupQueryMediaResponseDto queryScheduleGroupMedia(UmsScheduleGroupQueryMediaRequestDto requestDto) {
        return null;
    }
}
