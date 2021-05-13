package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.ums.requestvo.*;
import com.kedacom.device.unite.MediaScheduleInterface;
import com.kedacom.device.core.convert.UmsOperateConvert;
import com.kedacom.device.core.service.UmsOperateService;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MediaScheduleInterface mediaScheduleInterface;


    @Override
    public UmsScheduleGroupCreateResponseDto createScheduleGroup(UmsScheduleGroupCreateRequestDto request) {
        log.info("创建调度组入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupCreateRequest createRequest = UmsOperateConvert.INSTANCE.createScheduleGroup(request);
        log.info("创建调度组交互参数:{}", createRequest);
        UmsScheduleGroupCreateResponseDto response = mediaScheduleInterface.createScheduleGroup(ssid, createRequest);
        log.info("创建调度组应答:{}", response);
        return response;
    }

    @Override
    public Boolean deleteScheduleGroup(UmsScheduleGroupDeleteRequestDto request) {
        log.info("删除调度组入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupDeleteRequest deleteScheduleGroup = UmsOperateConvert.INSTANCE.deleteScheduleGroup(request);
        log.info("删除调度组交互参数:{}", deleteScheduleGroup);
        Boolean response = mediaScheduleInterface.deleteScheduleGroup(ssid, deleteScheduleGroup);
        log.info("删除调度组应答:{}", response);
        return response;
    }

    @Override
    public Boolean addScheduleGroupMember(UmsScheduleGroupAddMembersRequestDto request) {
        log.info("添加调度组成员设备入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupAddMembersRequest addScheduleGroupMember = UmsOperateConvert.INSTANCE.addScheduleGroupMember(request);
        log.info("添加调度组成员设备交互参数:{}", addScheduleGroupMember);
        Boolean response = mediaScheduleInterface.addScheduleGroupMember(ssid, addScheduleGroupMember);
        log.info("添加调度组成员设备应答:{}", response);
        return response;
    }

    @Override
    public Boolean deleteScheduleGroupMember(UmsScheduleGroupDeleteMembersRequestDto request) {
        log.info("删除调度组成员设备入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupDeleteMembersRequest deleteScheduleGroupMember = UmsOperateConvert.INSTANCE.deleteScheduleGroupMember(request);
        log.info("删除调度组成员设备交互参数:{}", deleteScheduleGroupMember);
        Boolean response = mediaScheduleInterface.deleteScheduleGroupMember(ssid, deleteScheduleGroupMember);
        log.info("删除调度组成员设备备应答:{}", response);
        return response;
    }

    @Override
    public List<UmsScheduleGroupQueryResponseDto> selectScheduleGroupList(UmsScheduleGroupQueryRequestDto request) {
        log.info("创建调度组入参:{}", request);

        Integer ssid = 1;
        List<UmsScheduleGroupQueryResponseDto> response = mediaScheduleInterface.selectScheduleGroupList(ssid);
        log.info("删除调度组成员设备备应答:{}", response);
        return response;
    }

    @Override
    public Boolean queryScheduleGroupStatus(UmsScheduleGroupQueryStatusRequestVo request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public Boolean setScheduleGroupSilence(UmsScheduleGroupSetSilenceRequestDto request) {
        log.info("设置调度组静音入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupSetSilenceRequest setSilenceRequest = UmsOperateConvert.INSTANCE.setScheduleGroupSilence(request);
        log.info("设置调度组静音交互参数:{}", setSilenceRequest);
        Boolean response = mediaScheduleInterface.setScheduleGroupSilence(ssid, setSilenceRequest);
        log.info("设置调度组静音应答:{}", response);
        return response;
    }

    @Override
    public UmsScheduleGroupQuerySilenceResponseDto queryScheduleGroupSilence(UmsScheduleGroupQuerySilenceRequestDto request) {
        log.info("查询调度组静音入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupQuerySilenceRequest silenceRequest = UmsOperateConvert.INSTANCE.queryScheduleGroupSilence(request);
        log.info("查询调度组静音交互参数:{}", silenceRequest);
        UmsScheduleGroupQuerySilenceResponseDto response = mediaScheduleInterface.queryScheduleGroupSilence(ssid, silenceRequest);
        log.info("查询调度组静音应答:{}", response);
        return response;
    }

    @Override
    public Boolean setScheduleGroupMute(UmsScheduleGroupSetMuteRequestDto request) {
        log.info("创建调度组入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupSetMuteRequest setMuteRequest = UmsOperateConvert.INSTANCE.setScheduleGroupMute(request);
        log.info("设置调度组静音交互参数:{}", setMuteRequest);
        Boolean response = mediaScheduleInterface.setScheduleGroupMute(ssid, setMuteRequest);
        log.info("设置调度组静音应答:{}", response);
        return response;
    }

    @Override
    public UmsScheduleGroupQueryMuteResponseDto queryScheduleGroupMute(UmsScheduleGroupQueryMuteRequestDto request) {
        log.info("查询调度组哑音入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupQueryMuteRequest queryMuteRequest = UmsOperateConvert.INSTANCE.queryScheduleGroupMute(request);
        log.info("查询调度组哑音交互参数:{}", queryMuteRequest);
        UmsScheduleGroupQueryMuteResponseDto response = mediaScheduleInterface.queryScheduleGroupMute(ssid, queryMuteRequest);
        log.info("查询调度组哑音应答:{}", response);
        return response;
    }

    @Override
    public Boolean controlScheduleGroupPtz(UmsScheduleGroupPtzControlRequestDto request) {
        log.info("调度组PTZ控制入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupPtzControlRequest ptzControlRequest = UmsOperateConvert.INSTANCE.controlScheduleGroupPtz(request);
        log.info("调度组PTZ控制交互参数:{}", ptzControlRequest);
        Boolean response = mediaScheduleInterface.controlScheduleGroupPtz(ssid, ptzControlRequest);
        log.info("调度组PTZ控制应答:{}", response);
        return response;
    }
    }

    @Override
    public List<String> joinScheduleGroupDiscussionGroup(UmsScheduleGroupJoinDiscussionGroupRequestDto request) {
        log.info("加入讨论组入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupJoinDiscussionGroupRequest joinDiscussionGroupRequest = UmsOperateConvert.INSTANCE.joinScheduleGroupDiscussionGroup(request);
        log.info("加入讨论组交互参数:{}", joinDiscussionGroupRequest);
        List<String> response = mediaScheduleInterface.joinScheduleGroupDiscussionGroup(ssid, joinDiscussionGroupRequest);
        log.info("加入讨论组应答:{}", response);
        return response;
    }

    @Override
    public Boolean quitScheduleGroupDiscussionGroup(UmsScheduleGroupQuitDiscussionGroupRequestDto request) {
        log.info("离开讨论组入参:{}", request);


        Integer ssid = 1;
        UmsScheduleGroupQuitDiscussionGroupRequest discussionGroupRequest = UmsOperateConvert.INSTANCE.quitScheduleGroupDiscussionGroup(request);
        log.info("离开讨论组交互参数:{}", discussionGroupRequest);
        Boolean response = mediaScheduleInterface.quitScheduleGroupDiscussionGroup(ssid, discussionGroupRequest);
        log.info("离开讨论组应答:{}", response);
        return response;
    }

    @Override
    public List<UmsScheduleGroupQueryDiscussionGroupResponseDto> queryScheduleGroupDiscussionGroup(UmsScheduleGroupQueryDiscussionGroupRequestDto request) {
        log.info("查询讨论组入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupQueryDiscussionGroupRequest groupRequest = UmsOperateConvert.INSTANCE.queryScheduleGroupDiscussionGroup(request);
        log.info("查询讨论组交互参数:{}", groupRequest);
        List<UmsScheduleGroupQueryDiscussionGroupResponseDto> response = mediaScheduleInterface.queryScheduleGroupDiscussionGroup(ssid, groupRequest);
        log.info("查询讨论组应答:{}", response);
        return response;
    }

    @Override
    public Boolean clearScheduleGroupDiscussionGroup(UmsScheduleGroupClearDiscussionGroupRequestDto request) {
        log.info("清空讨论组入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupClearDiscussionGroupRequest groupRequest = UmsOperateConvert.INSTANCE.clearScheduleGroupDiscussionGroup(request);
        log.info("清空讨论组交互参数:{}", groupRequest);
        Boolean response = mediaScheduleInterface.clearScheduleGroupDiscussionGroup(ssid, groupRequest);
        log.info("清空讨论组应答:{}", response);
        return response;
    }

    @Override
    public String startScheduleGroupVmpMix(UmsScheduleGroupStartVmpMixRequestDto request) {
        log.info("开始画面合成入参:{}", request);

        Integer ssid = 1;
        UmsScheduleGroupStartVmpMixRequest startVmpMixRequest = UmsOperateConvert.INSTANCE.startScheduleGroupVmpMix(request);
        log.info("开始画面合成交互参数:{}", startVmpMixRequest);
        String response = mediaScheduleInterface.startScheduleGroupVmpMix(ssid, startVmpMixRequest);
        log.info("开始画面合成应答:{}", response);
        return response;
    }

    @Override
    public UmsScheduleGroupUpdateVmpMixResponseDto updateScheduleGroupVmpMix(UmsScheduleGroupUpdateVmpMixRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public Boolean stopScheduleGroupVmpMix(UmsScheduleGroupStopVmpMixRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public UmsScheduleGroupQueryVmpMixResponseDto queryScheduleGroupVmpMix(UmsScheduleGroupQueryVmpMixRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public UmsStartRecResponseDto startRec(UmsStartRecRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public Boolean stopRec(UmsStopRecRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public UmsQueryRecListResponseDto queryRecList(UmsQueryRecListRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public Boolean callUpSubDevice(UmsScheduleGroupSubDeviceCallUpRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public Boolean setScheduleGroupBroadcast(UmsScheduleGroupSetBroadcastRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public Boolean cancelScheduleGroupBroadcast(UmsScheduleGroupCancelBroadcastRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public UmsScheduleGroupQueryBroadcastResponseDto cancelScheduleGroupBroadcast(UmsScheduleGroupQueryBroadcastRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public Boolean setScheduleGroupMedia(UmsScheduleGroupSetMediaRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }

    @Override
    public UmsScheduleGroupQueryMediaResponseDto queryScheduleGroupMedia(UmsScheduleGroupQueryMediaRequestDto request) {
        log.info("创建调度组入参:{}", request);

        return null;
    }
}
