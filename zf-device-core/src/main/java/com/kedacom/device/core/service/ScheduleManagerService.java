package com.kedacom.device.core.service;

import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
public interface ScheduleManagerService {

    /**
     * 创建调度组
     * @param requestDto
     * @return
     */
    UmsScheduleGroupCreateResponseDto createScheduleGroup(UmsScheduleGroupCreateRequestDto requestDto);

    /**
     * 删除调度组
     * @param requestDto
     * @return
     */
    Boolean deleteScheduleGroup(UmsScheduleGroupDeleteRequestDto requestDto);

    /**
     * 添加调度组成员设备
     * @param requestDto
     * @return
     */
    Boolean addScheduleGroupMember(UmsScheduleGroupAddMembersRequestDto requestDto);

    /**
     * 删除调度组成员设备
     * @param requestDto
     * @return
     */
    Boolean deleteScheduleGroupMember(UmsScheduleGroupDeleteMembersRequestDto requestDto);

    /**
     * 查询调度组集合
     * @param requestDto
     * @return
     */
    List<UmsScheduleGroupQueryResponseDto> selectScheduleGroupList(UmsScheduleGroupQueryRequestDto requestDto);

    /**
     * 查询调度组状态，查询成功后会通过kafka通知推送
     * @param requestDto
     * @return
     */
    Boolean queryScheduleGroupStatus(UmsScheduleGroupQueryStatusRequestVo requestDto);

    /**
     * 设置调度组静音
     * @param requestDto
     * @return
     */
    Boolean setScheduleGroupSilence(UmsScheduleGroupSetSilenceRequestDto requestDto);

    /**
     * 查询调度组静音
     * @param requestDto
     * @return
     */
    UmsScheduleGroupQuerySilenceResponseDto queryScheduleGroupSilence(UmsScheduleGroupQuerySilenceRequestDto requestDto);

    /**
     * 设置调度组哑音
     * @param requestDto
     * @return
     */
    Boolean setScheduleGroupMute(UmsScheduleGroupSetMuteRequestDto requestDto);

    /**
     * 查询调度组哑音
     * @param requestDto
     * @return
     */
    UmsScheduleGroupQueryMuteResponseDto queryScheduleGroupMute(UmsScheduleGroupQueryMuteRequestDto requestDto);

    /**
     * 调度组PTZ控制
     * @param requestDto
     * @return
     */
    Boolean controlScheduleGroupPtz(UmsScheduleGroupPtzControlRequestDto requestDto);

    /**
     * 开始画面合成
     * @param requestDto
     * @return
     */
    String startScheduleGroupVmpMix(UmsScheduleGroupStartVmpMixRequestDto requestDto);

    /**
     * 更新画面合成
     * @param requestDto
     * @return
     */
    Boolean updateScheduleGroupVmpMix(UmsScheduleGroupUpdateVmpMixRequestDto requestDto);

    /**
     * 停止画面合成
     * @param requestDto
     * @return
     */
    Boolean stopScheduleGroupVmpMix(UmsScheduleGroupStopVmpMixRequestDto requestDto);

    /**
     * 查询画面合成
     * @param requestDto
     * @return
     */
    UmsScheduleGroupQueryVmpMixResponseDto queryScheduleGroupVmpMix(UmsScheduleGroupQueryVmpMixRequestDto requestDto);

    /**
     * 呼叫设备上线
     * @param requestDto
     * @return
     */
    Boolean callUpSubDevice(UmsScheduleGroupSubDeviceCallUpRequestDto requestDto);

    /**
     * 设置调度组广播源
     * @param requestDto
     * @return
     */
    Boolean setScheduleGroupBroadcast(UmsScheduleGroupSetBroadcastRequestDto requestDto);

    /**
     * 取消调度组广播源
     * @param requestDto
     * @return
     */
    Boolean cancelScheduleGroupBroadcast(UmsScheduleGroupCancelBroadcastRequestDto requestDto);

    /**
     * 查询调度组广播源
     * @param requestDto
     * @return
     */
    UmsScheduleGroupQueryBroadcastResponseDto queryScheduleGroupBroadcast(UmsScheduleGroupQueryBroadcastRequestDto requestDto);

    /**
     * 设置调度组媒体源
     * @param requestDto
     * @return
     */
    Boolean setScheduleGroupMedia(UmsScheduleGroupSetMediaRequestDto requestDto);

    /**
     * 查询调度组媒体源
     * @param requestDto
     * @return
     */
    UmsScheduleGroupQueryMediaResponseDto queryScheduleGroupMedia(UmsScheduleGroupQueryMediaRequestDto requestDto);

}
