package com.kedacom.device.core.service.impl;

import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.avIntegration.response.tvwall.*;
import com.kedacom.device.core.data.DeviceConstants;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.exception.TvWallException;
import com.kedacom.device.core.msp.TvWallManageSdk;
import com.kedacom.device.core.service.TvWallService;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public TvWallListResponse ls(TvWallListRequest request) {
        log.info("获取所有大屏配置入参---TvWallListRequest:{}", request);
        TvWallListResponse response = tvWallManageSdk.ls(request);
        log.info("获取所有大屏配置应答---TvWallListResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvWallException(DeviceErrorEnum.TVWALL_LIST_FAILED.getCode(),DeviceErrorEnum.TVWALL_LIST_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvWallLayoutResponse layout(TvWallLayoutRequest request) {
        log.info("获取大屏布局入参---TvWallLayoutRequest:{}", request);
        TvWallLayoutResponse response = tvWallManageSdk.layout(request);
        log.info("获取大屏布局应答---TvWallLayoutResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvWallException(DeviceErrorEnum.TVWALL_LAYOUT_FAILED.getCode(),DeviceErrorEnum.TVWALL_LAYOUT_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvWallQueryPipelineResponse query(TvWallQueryPipelineRequest request) {
        log.info("查询虚拟屏入参---TvWallQueryPipelineRequest:{}", request);
        TvWallQueryPipelineResponse response = tvWallManageSdk.query(request);
        log.info("查询虚拟屏应答---TvWallQueryPipelineResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvWallException(DeviceErrorEnum.TVWALL_QUERY_PIPELINE_FAILED.getCode(),DeviceErrorEnum.TVWALL_QUERY_PIPELINE_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvWallConfigResponse config(TvWallConfigRequest request) {
        log.info("配置虚拟屏入参---TvWallConfigRequest:{}", request);
        TvWallConfigResponse response = tvWallManageSdk.config(request);
        log.info("配置虚拟屏应答---TvWallConfigResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvWallException(DeviceErrorEnum.TVWALL_CONFIG_FAILED.getCode(),DeviceErrorEnum.TVWALL_CONFIG_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvWallPipelineBindResponse configBind(TvWallPipelineBindRequest request) {
        log.info("配置虚拟屏窗口与资源的绑定关系入参---TvWallPipelineBindRequest:{}", request);
        TvWallPipelineBindResponse response = tvWallManageSdk.configBind(request);
        log.info("配置虚拟屏窗口与资源的绑定关系应答---TvWallPipelineBindResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvWallException(DeviceErrorEnum.TVWALL_PIPELINE_BIND_FAILED.getCode(),DeviceErrorEnum.TVWALL_PIPELINE_BIND_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvWallDeleteResponse delete(TvWallDeleteRequest request) {
        log.info("大屏删除入参---TvWallDeleteRequest:{}", request);
        TvWallDeleteResponse response = tvWallManageSdk.delete(request);
        log.info("大屏删除应答---TvWallDeleteResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvWallException(DeviceErrorEnum.TVWALL_DELETE_FAILED.getCode(),DeviceErrorEnum.TVWALL_DELETE_FAILED.getMsg());
        }
        return response;
    }
}
