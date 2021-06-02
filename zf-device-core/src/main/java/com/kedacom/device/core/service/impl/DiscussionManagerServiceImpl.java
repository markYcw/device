package com.kedacom.device.core.service.impl;

import com.kedacom.device.core.convert.UmsSubDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.exception.UmsOperateException;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.service.DiscussionManagerService;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.ClearDiscussionGroupRequest;
import com.kedacom.device.ums.request.JoinDiscussionGroupRequest;
import com.kedacom.device.ums.request.QueryDiscussionGroupRequest;
import com.kedacom.device.ums.request.QuitDiscussionGroupRequest;
import com.kedacom.device.ums.response.ClearDiscussionGroupResponse;
import com.kedacom.device.ums.response.JoinDiscussionGroupResponse;
import com.kedacom.device.ums.response.QueryDiscussionGroupResponse;
import com.kedacom.device.ums.response.QuitDiscussionGroupResponse;
import com.kedacom.ums.requestdto.UmsScheduleGroupClearDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupJoinDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupQueryDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupQuitDiscussionGroupRequestDto;
import com.kedacom.ums.responsedto.UmsScheduleGroupQueryDiscussionGroupResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/2
 */
@Slf4j
@Service
public class DiscussionManagerServiceImpl implements DiscussionManagerService {

    @Resource
    UmsClient umsClient;

    @Resource
    DeviceMapper deviceMapper;

    @Override
    public List<String> joinScheduleGroupDiscussionGroup(UmsScheduleGroupJoinDiscussionGroupRequestDto request) {

        log.info("加入讨论组入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        JoinDiscussionGroupRequest joinDiscussionGroupRequest = UmsSubDeviceConvert.INSTANCE.convertJoinDiscussionGroupRequest(request);
        joinDiscussionGroupRequest.setSsid(Integer.valueOf(sessionId));
        JoinDiscussionGroupResponse response = umsClient.joindiscussion(joinDiscussionGroupRequest);
        if (response.acquireErrcode() != 0) {
            log.error("加入讨论组失败");
            throw new UmsOperateException("加入讨论组失败");
        }

        return response.getDeviceIDs();
    }

    @Override
    public Boolean quitScheduleGroupDiscussionGroup(UmsScheduleGroupQuitDiscussionGroupRequestDto request) {

        log.info("离开讨论组入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        QuitDiscussionGroupRequest quitDiscussionGroupRequest = new QuitDiscussionGroupRequest();
        quitDiscussionGroupRequest.setSsid(Integer.valueOf(sessionId));
        quitDiscussionGroupRequest.setDeviceID(request.getDeviceID());
        quitDiscussionGroupRequest.setGroupID(request.getGroupID());
        QuitDiscussionGroupResponse response = umsClient.quitdiscussion(quitDiscussionGroupRequest);
        if (response.acquireErrcode() != 0) {
            log.error("退出讨论组失败");
            throw new UmsOperateException("退出讨论组失败");
        }

        return true;
    }

    @Override
    public List<UmsScheduleGroupQueryDiscussionGroupResponseDto> queryScheduleGroupDiscussionGroup(UmsScheduleGroupQueryDiscussionGroupRequestDto request) {

        log.info("查询讨论组入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        QueryDiscussionGroupRequest queryDiscussionGroupRequest = new QueryDiscussionGroupRequest();
        queryDiscussionGroupRequest.setSsid(Integer.valueOf(sessionId));
        QueryDiscussionGroupResponse response = umsClient.querydiscussion(queryDiscussionGroupRequest);
        if (response.acquireErrcode() != 0) {
            log.error("查询讨论组失败");
            throw new UmsOperateException("查询讨论组失败");
        }

        return response.getMembers();
    }

    @Override
    public Boolean clearScheduleGroupDiscussionGroup(UmsScheduleGroupClearDiscussionGroupRequestDto request) {

        log.info("清空讨论组入参:{}", request);
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        String sessionId = entity.getSessionId();
        ClearDiscussionGroupRequest clearDiscussionGroupRequest = new ClearDiscussionGroupRequest();
        clearDiscussionGroupRequest.setSsid(Integer.valueOf(sessionId));
        clearDiscussionGroupRequest.setGroupID(request.getGroupID());
        ClearDiscussionGroupResponse response = umsClient.cleardiscussion(clearDiscussionGroupRequest);
        if (response.acquireErrcode() != 0) {
            log.error("清空讨论组失败");
            throw new UmsOperateException("清空讨论组失败");
        }

        return true;
    }

}
