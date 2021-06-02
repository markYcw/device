package com.kedacom.device.core.service.impl;

import com.kedacom.device.core.convert.UmsSubDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.exception.UmsOperateException;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.service.ScheduleManagerService;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.*;
import com.kedacom.device.ums.response.*;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Slf4j
@Service
public class ScheduleManagerServiceImpl implements ScheduleManagerService {

    @Resource
    UmsClient umsClient;

    @Resource
    DeviceMapper deviceMapper;

    @Override
    public UmsScheduleGroupCreateResponseDto createScheduleGroup(UmsScheduleGroupCreateRequestDto request) {

        log.info("创建调度组入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        CreateRequest createRequest = new CreateRequest();
        createRequest.setSsid(Integer.valueOf(sessionId));
        createRequest.setMembers(request.getMembers());
        CreateResponse response = umsClient.creategroup(createRequest);
        if (response.acquireErrcode() != 0) {
            log.error("创建调度组失败");
            throw new UmsOperateException("创建调度组失败");
        }

        return response.acquireData(UmsScheduleGroupCreateResponseDto.class);
    }

    @Override
    public Boolean deleteScheduleGroup(UmsScheduleGroupDeleteRequestDto request) {

        log.info("删除调度组入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setSsid(Integer.valueOf(sessionId));
        deleteRequest.setGroupID(request.getGroupID());
        DeleteResponse response = umsClient.deletegroup(deleteRequest);
        if (response.acquireErrcode() != 0) {
            log.error("删除调度组失败");
            throw new UmsOperateException("删除调度组失败");
        }

        return true;
    }

    @Override
    public Boolean addScheduleGroupMember(UmsScheduleGroupAddMembersRequestDto request) {

        log.info("添加调度组成员设备入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        AddMembersRequest addMembersRequest = UmsSubDeviceConvert.INSTANCE.convertAddMembersRequest(request);
        addMembersRequest.setSsid(Integer.valueOf(sessionId));
        AddMembersResponse response = umsClient.addgroupmember(addMembersRequest);
        if (response.acquireErrcode() != 0) {
            log.error("添加调度组成员设备失败");
            throw new UmsOperateException("添加调度组成员设备失败");
        }

        return true;
    }

    @Override
    public Boolean deleteScheduleGroupMember(UmsScheduleGroupDeleteMembersRequestDto request) {

        log.info("删除调度组成员设备入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        DeleteMembersRequest deleteMembersRequest = new DeleteMembersRequest();
        deleteMembersRequest.setSsid(Integer.valueOf(sessionId));
        deleteMembersRequest.setDeviceID(request.getDeviceID());
        deleteMembersRequest.setGroupID(request.getGroupID());
        DeleteMembersResponse response = umsClient.deletegroupmember(deleteMembersRequest);
        if (response.acquireErrcode() != 0) {
            log.error("删除调度组成员设备失败");
            throw new UmsOperateException("删除调度组成员设备失败");
        }

        return true;
    }

    @Override
    public List<UmsScheduleGroupQueryResponseDto> selectScheduleGroupList(UmsScheduleGroupQueryRequestDto request) {

        log.info("查询调度组入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        QueryScheduleGroupRequest queryScheduleGroupRequest = new QueryScheduleGroupRequest();
        queryScheduleGroupRequest.setSsid(Integer.valueOf(sessionId));
        QueryScheduleGroupResponse response = umsClient.querygroup(queryScheduleGroupRequest);
        if (response.acquireErrcode() != 0) {
            log.error("查询调度组失败");
            throw new UmsOperateException("查询调度组失败");
        }

        return response.getGroups();
    }

    @Override
    public Boolean queryScheduleGroupStatus(UmsScheduleGroupQueryStatusRequestVo request) {

        log.info("查询调度组状态入参:{}", request);


        return null;
    }

    @Override
    public Boolean setScheduleGroupSilence(UmsScheduleGroupSetSilenceRequestDto request) {

        log.info("设置调度组静音入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        SetSilenceRequest setSilenceRequest = UmsSubDeviceConvert.INSTANCE.convertSetSilenceRequest(request);
        setSilenceRequest.setSsid(Integer.valueOf(sessionId));
        SetSilenceResponse response = umsClient.setgroupsilence(setSilenceRequest);
        if (response.acquireErrcode() != 0) {
            log.error("设置调度组静音失败");
            throw new UmsOperateException("设置调度组静音失败");
        }

        return true;
    }

    @Override
    public UmsScheduleGroupQuerySilenceResponseDto queryScheduleGroupSilence(UmsScheduleGroupQuerySilenceRequestDto request) {

        log.info("查询调度组静音入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        QuerySilenceRequest querySilenceRequest = new QuerySilenceRequest();
        querySilenceRequest.setSsid(Integer.valueOf(sessionId));
        querySilenceRequest.setDeviceID(request.getDeviceID());
        querySilenceRequest.setGroupID(request.getGroupID());
        QuerySilenceResponse response = umsClient.querygroupsilence(querySilenceRequest);
        if (response.acquireErrcode() != 0) {
            log.error("查询调度组静音失败");
            throw new UmsOperateException("查询调度组静音失败");
        }

        return response.acquireData(UmsScheduleGroupQuerySilenceResponseDto.class);
    }

    @Override
    public Boolean setScheduleGroupMute(UmsScheduleGroupSetMuteRequestDto request) {

        log.info("设置调度组哑音入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        SetMuteRequest setMuteRequest = UmsSubDeviceConvert.INSTANCE.convetSetMuteRequest(request);
        setMuteRequest.setSsid(Integer.valueOf(sessionId));
        SetMuteResponse response = umsClient.setgroupmute(setMuteRequest);
        if (response.acquireErrcode() != 0) {
            log.error("设置调度组哑音失败");
            throw new UmsOperateException("设置调度组哑音失败");
        }

        return true;
    }

    @Override
    public UmsScheduleGroupQueryMuteResponseDto queryScheduleGroupMute(UmsScheduleGroupQueryMuteRequestDto request) {

        log.info("查询调度组哑音入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        QueryMuteRequest queryMuteRequest = new QueryMuteRequest();
        queryMuteRequest.setSsid(Integer.valueOf(sessionId));
        queryMuteRequest.setDeviceID(request.getDeviceID());
        queryMuteRequest.setGroupID(request.getGroupID());
        QueryMuteResponse response = umsClient.querygroupmute(queryMuteRequest);
        if (response.acquireErrcode() != 0) {
            log.error("查询调度组哑音失败");
            throw new UmsOperateException("查询调度组哑音失败");
        }

        return response.acquireData(UmsScheduleGroupQueryMuteResponseDto.class);
    }

    @Override
    public Boolean controlScheduleGroupPtz(UmsScheduleGroupPtzControlRequestDto request) {

        log.info("调度组PTZ控制入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        PtzControlRequest ptzControlRequest = UmsSubDeviceConvert.INSTANCE.convertPtzControlRequest(request);
        ptzControlRequest.setSsid(Integer.valueOf(sessionId));
        PtzControlResponse response = umsClient.groupptz(ptzControlRequest);
        if (response.acquireErrcode() != 0) {
            log.error("调度组PTZ控制失败");
            throw new UmsOperateException("调度组PTZ控制失败");
        }

        return true;
    }

    @Override
    public String startScheduleGroupVmpMix(UmsScheduleGroupStartVmpMixRequestDto request) {

        log.info("开始画面合成入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        StartVmpMixRequest startVmpMixRequest = UmsSubDeviceConvert.INSTANCE.convertStartVmpMixRequest(request);
        startVmpMixRequest.setSsid(Integer.valueOf(sessionId));
        StartVmpMixResponse response = umsClient.startvmpmix(startVmpMixRequest);
        if (response.acquireErrcode() !=0) {
            log.error("开始画面合成失败");
            throw new UmsOperateException("开始画面合成失败");
        }

        return response.getVmpMixID();
    }

    @Override
    public Boolean updateScheduleGroupVmpMix(UmsScheduleGroupUpdateVmpMixRequestDto request) {

        log.info("更新画面合成入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        UpdateVmpMixRequest updateVmpMixRequest = UmsSubDeviceConvert.INSTANCE.convertUpdateVmpMixRequest(request);
        updateVmpMixRequest.setSsid(Integer.valueOf(sessionId));
        UpdateVmpMixResponse response = umsClient.updatevmpmix(updateVmpMixRequest);
        if (response.acquireErrcode() != 0) {
            log.error("更新画面合成失败");
            throw new UmsOperateException("更新画面合成失败");
        }

        return true;
    }

    @Override
    public Boolean stopScheduleGroupVmpMix(UmsScheduleGroupStopVmpMixRequestDto request) {

        log.info("停止画面合成入参:{}", request);
        String sessionId = deviceMapper.selectById(request.getUmsId()).getSessionId();
        StopVmpMixRequest stopVmpMixRequest = new StopVmpMixRequest();
        stopVmpMixRequest.setSsid(Integer.valueOf(sessionId));
        stopVmpMixRequest.setGroupID(request.getGroupId());
        StopVmpMixResponse response = umsClient.stopvmpmix(stopVmpMixRequest);
        if (response.acquireErrcode() != 0) {
            log.error("停止画面合成失败");
            throw new UmsOperateException("停止画面合成失败");
        }

        return true;
    }

    @Override
    public UmsScheduleGroupQueryVmpMixResponseDto queryScheduleGroupVmpMix(UmsScheduleGroupQueryVmpMixRequestDto request) {

        log.info("查询画面合成入参:{}", request);
        String groupId = request.getGroupId();
        String sessionId = deviceMapper.selectById(request.getUmsId()).getSessionId();
        QueryVmpMixRequest queryVmpMixRequest = new QueryVmpMixRequest();
        queryVmpMixRequest.setSsid(Integer.valueOf(sessionId));
        queryVmpMixRequest.setGroupID(groupId);
        QueryVmpMixResponse response = umsClient.queryvmpmix(queryVmpMixRequest);
        if (response.acquireErrcode() != 0) {
            log.error("查询画面合成失败");
            throw new UmsOperateException("查询画面合成失败");
        }

        return response.acquireData(UmsScheduleGroupQueryVmpMixResponseDto.class);
    }

    @Override
    public Boolean callUpSubDevice(UmsScheduleGroupSubDeviceCallUpRequestDto request) {

        log.info("呼叫设备上线入参:{}", request);
        String umsId = request.getUmsId();
        String sessionId = deviceMapper.selectById(umsId).getSessionId();
        List<UmsScheduleGroupSubDeviceCallMembersRequestDto> callMembers = request.getCallMembers();
        CallUpRequest callUpRequest = new CallUpRequest();
        callUpRequest.setSsid(Integer.valueOf(sessionId));
        callUpRequest.setGroupID(request.getGroupId());
        callUpRequest.setCallMembers(callMembers);
        CallUpResponse response = umsClient.callmember(callUpRequest);
        if (response.acquireErrcode() != 0) {
            log.error("呼叫设备上线失败");
            throw new UmsOperateException("呼叫设备上线失败");
        }

        return true;
    }

    @Override
    public Boolean setScheduleGroupBroadcast(UmsScheduleGroupSetBroadcastRequestDto request) {

        log.info("设置调度组广播源入参 : {}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        String groupId = request.getRequestDto().getGroupID();
        List<QueryMediaResponseDto> members = request.getRequestDto().getMembers();
        SetBroadcastRequest broadcastRequest = new SetBroadcastRequest();
        broadcastRequest.setSsid(Integer.valueOf(sessionId));
        broadcastRequest.setBroadcast(members);
        broadcastRequest.setGroupID(groupId);
        SetBroadcastResponse response = umsClient.setbroadcast(broadcastRequest);
        if (response.acquireErrcode() != 0) {
            log.error("设置调度组广播源失败");
            throw new UmsOperateException("设置调度组广播源失败");
        }

        return true;
    }

    @Override
    public Boolean cancelScheduleGroupBroadcast(UmsScheduleGroupCancelBroadcastRequestDto request) {

        log.info("取消调度组广播源入参:{}", request);
        DeviceInfoEntity entity = deviceMapper.selectById(request.getUmsId());
        String sessionId = entity.getSessionId();
        CancelBroadcastRequest broadcastRequest = new CancelBroadcastRequest();
        broadcastRequest.setSsid(Integer.valueOf(sessionId));
        broadcastRequest.setGroupID(request.getGroupId());
        CancelBroadcastResponse response = umsClient.cancelbroadcast(broadcastRequest);
        if (response.acquireErrcode() != 0) {
            log.error("取消调度组广播源失败");
            throw new UmsOperateException("取消调度组广播源失败");
        }

        return true;
    }

    @Override
    public UmsScheduleGroupQueryBroadcastResponseDto queryScheduleGroupBroadcast(UmsScheduleGroupQueryBroadcastRequestDto request) {

        log.info("查询调度组广播源入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsId);
        String sessionId = deviceInfoEntity.getSessionId();
        QueryBroadcastRequest broadcastRequest = new QueryBroadcastRequest();
        broadcastRequest.setSsid(Integer.valueOf(sessionId));
        broadcastRequest.setGroupID(request.getGroupId());
        QueryBroadcastResponse response = umsClient.querybroadcast(broadcastRequest);
        if (response.acquireErrcode() != 0) {
            log.error("查询调度组广播源失败");
            throw new UmsOperateException("查询调度组广播源失败");
        }

        return response.acquireData(UmsScheduleGroupQueryBroadcastResponseDto.class);
    }

    @Override
    public Boolean setScheduleGroupMedia(UmsScheduleGroupSetMediaRequestDto requestDto) {

        log.info("设置调度组成员媒体源入参:{}", requestDto);
        String umsId = requestDto.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        UmsScheduleGroupQueryMediaResponseDto mediaInfo = requestDto.getMediaInfo();
        SetMediaRequest request = UmsSubDeviceConvert.INSTANCE.convertSetMediaRequest(mediaInfo);
        request.setSsid(Integer.valueOf(sessionId));
        SetMediaResponse response = umsClient.setscheduling(request);
        if (response.acquireErrcode() != 0) {
            log.error("设置调度组成员媒体源失败");
            throw new UmsOperateException("设置调度组成员媒体源失败");
        }

        return true;
    }

    @Override
    public UmsScheduleGroupQueryMediaResponseDto queryScheduleGroupMedia(UmsScheduleGroupQueryMediaRequestDto requestDto) {

        log.info("查询调度组成员媒体源入参:{}", requestDto);
        String umsId = requestDto.getUmsId();
        String groupId = requestDto.getGroupId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        QueryMediaRequest request = new QueryMediaRequest();
        request.setSsid(Integer.valueOf(sessionId));
        request.setGroupID(groupId);
        QueryMediaResponse response = umsClient.queryscheduling(request);
        if (response.acquireErrcode() != 0) {
            log.error("查询调度组成员媒体源信息失败");
            throw new UmsOperateException("查询调度组成员媒体源信息失败");
        }
        log.info("查询调度组成员媒体源返回参数:{}", response);

        return response.acquireData(UmsScheduleGroupQueryMediaResponseDto.class);

    }
}
