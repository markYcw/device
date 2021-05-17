package com.kedacom.device.core.convert;

import com.kedacom.acl.network.ums.responsevo.SubDeviceInfoResponseVo;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.ums.request.*;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.UmsScheduleGroupQueryMediaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Mapper
public interface UmsSubDeviceConvert {

    UmsSubDeviceConvert INSTANCE = Mappers.getMapper(UmsSubDeviceConvert.class);

    List<SubDeviceInfoEntity> convertSubDeviceInfoEntityList(List<SubDeviceInfoResponseVo> responseVoList);

    SetMediaRequest convertSetMediaRequest(UmsScheduleGroupQueryMediaResponseDto requestDto);

    UpdateVmpMixRequest convertUpdateVmpMixRequest(UmsScheduleGroupUpdateVmpMixRequestDto requestDto);

    StartVmpMixRequest convertStartVmpMixRequest(UmsScheduleGroupStartVmpMixRequestDto requestDto);

    JoinDiscussionGroupRequest convertJoinDiscussionGroupRequest(UmsScheduleGroupJoinDiscussionGroupRequestDto requestDto);

    PtzControlRequest convertPtzControlRequest(UmsScheduleGroupPtzControlRequestDto requestDto);

    SetMuteRequest convetSetMuteRequest(UmsScheduleGroupSetMuteRequestDto requestDto);

    SetSilenceRequest convertSetSilenceRequest(UmsScheduleGroupSetSilenceRequestDto requestDto);

    AddMembersRequest convertAddMembersRequest(UmsScheduleGroupAddMembersRequestDto requestDto);

}
