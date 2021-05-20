package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.data.avIntegration.tvwall.*;
import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.msp.TvWallManageSdk;
import com.kedacom.device.core.service.TvWallService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:47
 */
@Service
@Slf4j
public class TvWallServiceImpl implements TvWallService {

    @Resource
    private TvWallManageSdk tvWallManageSdk;
    @Autowired
    private HandleResponseUtil responseUtil;

    @Override
    public TvWallListResponse ls(TvWallListRequest request) {
        log.info("获取所有大屏配置入参:{}", request);
        TvWallListResponse response = tvWallManageSdk.ls(request);
        log.info("获取所有大屏配置应答:{}", response);
        responseUtil.handleMSPRes("获取所有大屏配置异常:{},{},{}", DeviceErrorEnum.TV_WALL_LIST_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public TvWallLayoutResponse layout(TvWallLayoutRequest request) {
        log.info("获取大屏布局入参:{}", request);
        TvWallLayoutResponse response = tvWallManageSdk.layout(request);
        log.info("获取大屏布局应答:{}", response);
        responseUtil.handleMSPRes("获取大屏布局异常:{},{},{}", DeviceErrorEnum.TV_WALL_LAYOUT_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public TvWallQueryPipelineResponse query(TvWallQueryPipelineRequest request) {
        log.info("查询虚拟屏入参:{}", request);
        TvWallQueryPipelineResponse response = tvWallManageSdk.query(request);
        log.info("查询虚拟屏应答:{}", response);
        responseUtil.handleMSPRes("查询虚拟屏异常:{},{},{}", DeviceErrorEnum.TV_WALL_QUERY_PIPELINE_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public TvWallConfigResponse config(TvWallConfigRequest request) {
        log.info("配置虚拟屏入参:{}", request);
        TvWallConfigResponse response = tvWallManageSdk.config(request);
        log.info("配置虚拟屏应答:{}", response);
        responseUtil.handleMSPRes("配置虚拟屏异常:{},{},{}", DeviceErrorEnum.TV_WALL_CONFIG_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public TvWallPipelineBindResponse configBind(TvWallPipelineBindRequest request) {
        log.info("配置虚拟屏窗口与资源的绑定关系入参:{}", request);
        TvWallPipelineBindResponse response = tvWallManageSdk.configBind(request);
        log.info("配置虚拟屏窗口与资源的绑定关系应答-:{}", response);
        responseUtil.handleMSPRes("配置虚拟屏窗口与资源的绑定关系异常:{},{},{}", DeviceErrorEnum.TV_WALL_PIPELINE_BIND_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public void delete(TvWallDeleteRequest request) {
        log.info("大屏删除入参:{}", request);
        TvWallDeleteResponse response = tvWallManageSdk.delete(request);
        log.info("大屏删除应答:{}", response);
        responseUtil.handleMSPRes("获取所有大屏配置异常:{},{},{}", DeviceErrorEnum.TV_WALL_DELETE_FAILED, response.getError(), null);
    }


}
