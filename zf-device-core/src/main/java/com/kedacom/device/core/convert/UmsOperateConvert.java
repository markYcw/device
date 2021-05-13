package com.kedacom.device.core.convert;

import com.kedacom.acl.network.ums.requestvo.*;
import com.kedacom.ums.requestdto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 14:39
 */
@Mapper(componentModel = "spring")
public interface UmsOperateConvert {

    UmsOperateConvert INSTANCE = Mappers.getMapper(UmsOperateConvert.class);

    UmsScheduleGroupCreateRequest createScheduleGroup(UmsScheduleGroupCreateRequestDto request);

    UmsScheduleGroupDeleteRequest deleteScheduleGroup(UmsScheduleGroupDeleteRequestDto request);

    UmsScheduleGroupAddMembersRequest addScheduleGroupMember(UmsScheduleGroupAddMembersRequestDto request);

    UmsScheduleGroupDeleteMembersRequest deleteScheduleGroupMember(UmsScheduleGroupDeleteMembersRequestDto request);

    UmsScheduleGroupSetSilenceRequest setScheduleGroupSilence(UmsScheduleGroupSetSilenceRequestDto request);

    UmsScheduleGroupQuerySilenceRequest queryScheduleGroupSilence(UmsScheduleGroupQuerySilenceRequestDto request);

    UmsScheduleGroupSetMuteRequest setScheduleGroupMute(UmsScheduleGroupSetMuteRequestDto request);

    UmsScheduleGroupQueryMuteRequest queryScheduleGroupMute(UmsScheduleGroupQueryMuteRequestDto request);

    UmsScheduleGroupPtzControlRequest controlScheduleGroupPtz(UmsScheduleGroupPtzControlRequestDto request);

    UmsScheduleGroupJoinDiscussionGroupRequest joinScheduleGroupDiscussionGroup(UmsScheduleGroupJoinDiscussionGroupRequestDto request);

    UmsScheduleGroupQuitDiscussionGroupRequest quitScheduleGroupDiscussionGroup(UmsScheduleGroupQuitDiscussionGroupRequestDto request);

    UmsScheduleGroupQueryDiscussionGroupRequest queryScheduleGroupDiscussionGroup(UmsScheduleGroupQueryDiscussionGroupRequestDto request);

    UmsScheduleGroupClearDiscussionGroupRequest clearScheduleGroupDiscussionGroup(UmsScheduleGroupClearDiscussionGroupRequestDto request);

    UmsScheduleGroupStartVmpMixRequest startScheduleGroupVmpMix(UmsScheduleGroupStartVmpMixRequestDto request);

}
