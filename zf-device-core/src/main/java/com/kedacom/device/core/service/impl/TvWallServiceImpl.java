package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.acl.network.data.avIntegration.tvwall.*;
import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.service.TvWallService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:47
 */
@Service
@Slf4j
public class TvWallServiceImpl implements TvWallService {

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Value("${zf.msp.server_addr}")
    private String mspUrl;

    private static final String mspPath = "/api/v1/manage/tvwall/";

    @Override
    public TvWallListResponse ls(TvWallListRequest request) {
        log.info("获取所有大屏配置入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "ls", JSON.toJSONString(request), String.class);
        TvWallListResponse tvWallListResponse = JSONObject.parseObject(response, TvWallListResponse.class);

        log.info("获取所有大屏配置应答:{}", tvWallListResponse);
        if (tvWallListResponse != null) {
            responseUtil.handleMSPRes("获取所有大屏配置异常:{},{},{}", DeviceErrorEnum.TV_WALL_LIST_FAILED, tvWallListResponse.getError(), null);
        }
        return tvWallListResponse;
    }

    @Override
    public TvWallLayoutResponse layout(TvWallLayoutRequest request) {
        log.info("获取大屏布局入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "layout", JSON.toJSONString(request), String.class);
        TvWallLayoutResponse tvWallListResponse = JSONObject.parseObject(response, TvWallLayoutResponse.class);

        log.info("获取大屏布局应答:{}", response);
        if (tvWallListResponse != null) {
            responseUtil.handleMSPRes("获取大屏布局异常:{},{},{}", DeviceErrorEnum.TV_WALL_LAYOUT_FAILED, tvWallListResponse.getError(), null);
        }
        return tvWallListResponse;
    }

    @Override
    public TvWallQueryPipelineResponse query(TvWallQueryPipelineRequest request) {
        log.info("查询虚拟屏入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "query", JSON.toJSONString(request), String.class);
        TvWallQueryPipelineResponse queryPipelineResponse = JSONObject.parseObject(response, TvWallQueryPipelineResponse.class);

        log.info("查询虚拟屏应答:{}", queryPipelineResponse);
        if (queryPipelineResponse != null) {
            responseUtil.handleMSPRes("查询虚拟屏异常:{},{},{}", DeviceErrorEnum.TV_WALL_QUERY_PIPELINE_FAILED, queryPipelineResponse.getError(), null);
        }
        return queryPipelineResponse;
    }

    @Override
    public TvWallConfigResponse config(TvWallConfigRequest request) {
        log.info("配置虚拟屏入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "config", JSON.toJSONString(request), String.class);
        TvWallConfigResponse configResponse = JSONObject.parseObject(response, TvWallConfigResponse.class);

        log.info("配置虚拟屏应答:{}", configResponse);
        if (configResponse != null) {
            responseUtil.handleMSPRes("配置虚拟屏异常:{},{},{}", DeviceErrorEnum.TV_WALL_CONFIG_FAILED, configResponse.getError(), null);
        }
        return configResponse;
    }

    @Override
    public TvWallPipelineBindResponse configBind(TvWallPipelineBindRequest request) {
        log.info("配置虚拟屏窗口与资源的绑定关系入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "config/bind", JSON.toJSONString(request), String.class);
        TvWallPipelineBindResponse bindResponse = JSONObject.parseObject(response, TvWallPipelineBindResponse.class);

        log.info("配置虚拟屏窗口与资源的绑定关系应答-:{}", bindResponse);
        if (bindResponse != null) {
            responseUtil.handleMSPRes("配置虚拟屏窗口与资源的绑定关系异常:{},{},{}", DeviceErrorEnum.TV_WALL_PIPELINE_BIND_FAILED, bindResponse.getError(), null);
        }
        return bindResponse;
    }

    @Override
    public void delete(TvWallDeleteRequest request) {
        log.info("大屏删除入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "delete", JSON.toJSONString(request), String.class);
        TvWallDeleteResponse deleteResponse = JSONObject.parseObject(response, TvWallDeleteResponse.class);

        log.info("大屏删除应答:{}", deleteResponse);
        if (deleteResponse != null) {
            responseUtil.handleMSPRes("获取所有大屏配置异常:{},{},{}", DeviceErrorEnum.TV_WALL_DELETE_FAILED, deleteResponse.getError(), null);
        }
    }


}
