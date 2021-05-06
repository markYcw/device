package com.kedacom.device.service.impl;

import com.kedacom.BaseResult;
import com.kedacom.acl.network.unite.AvIntegrationInterface;
import com.kedacom.avIntegration.request.*;
import com.kedacom.avIntegration.response.*;
import com.kedacom.device.service.TvWallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 11:00
 */
@Service
@Slf4j
public class TvWallServiceImpl implements TvWallService {

    @Resource
    private AvIntegrationInterface avIntegrationInterface;

    @Override
    public BaseResult<TvWallListResponse> ls(TvWallListRequest request) {
        log.info("获取所有大屏配置入参--TvWallListRequest:{}", request);
        BaseResult<TvWallListResponse> response = avIntegrationInterface.ls(request);
        log.info("获取所有大屏配置应答--TvWallListResponse:{}", response);
        return response;
    }

    @Override
    public BaseResult<TvWallLayoutResponse> layout(TvWallLayoutRequest request) {
        log.info("获取大屏布局入参--TvWallLayoutRequest:{}", request);
        BaseResult<TvWallLayoutResponse> response = avIntegrationInterface.layout(request);
        log.info("获取大屏布局应答--TvWallLayoutResponse:{}", response);
        return response;
    }

    @Override
    public BaseResult<TvWallQueryPipelineResponse> query(TvWallQueryPipelineRequest request) {
        log.info("查询大屏通道绑定（虚拟屏）入参--TvWallQueryPipelineRequest:{}", request);
        BaseResult<TvWallQueryPipelineResponse> response = avIntegrationInterface.query(request);
        log.info("查询大屏通道绑定（虚拟屏）应答--TvWallQueryPipelineResponse:{}", response);
        return response;
    }

    @Override
    public BaseResult<TvWallConfigResponse> config(TvWallConfigRequest request) {
        log.info("设置大屏（虚拟屏）入参--TvWallConfigRequest:{}", request);
        BaseResult<TvWallConfigResponse> response = avIntegrationInterface.config(request);
        log.info("设置大屏（虚拟屏）应答--TvWallConfigResponse:{}", response);
        return response;
    }

    @Override
    public BaseResult<TvWallPipelineBindResponse> configBind(TvWallPipelineBindRequest request) {
        log.info("配置大屏通道绑定（虚拟屏）入参--TvWallPipelineBindRequest:{}", request);
        BaseResult<TvWallPipelineBindResponse> response = avIntegrationInterface.configBind(request);
        log.info("配置大屏通道绑定（虚拟屏）应答--TvWallPipelineBindResponse:{}", response);
        return response;
    }

    @Override
    public BaseResult delete(TvWallDeleteRequest request) {
        log.info("删除大屏（虚拟屏）入参--TvWallDeleteRequest:{}", request);
        BaseResult response = avIntegrationInterface.delete(request);
        log.info("删除大屏（虚拟屏）应答--BaseResult:{}", response);
        return response;
    }
}
