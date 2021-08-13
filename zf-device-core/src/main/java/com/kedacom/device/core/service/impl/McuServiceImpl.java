package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.kedacom.BaseResult;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.McuConvert;
import com.kedacom.device.core.mapper.UmsMeetingPlatformMapper;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.McuUrlFactory;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import com.kedacom.device.meetingPlatform.mcu.request.McuLoginRequest;
import com.kedacom.device.meetingPlatform.mcu.response.McuLoginResponse;
import com.kedacom.meeting.mcu.McuRequestDTO;
import com.kedacom.meeting.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.meeting.mcu.response.McuLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author hxj
 * @date: 2021/8/13 13:50
 * @description 会议平台业务类
 */
@Service(value = "mcuService")
@Slf4j
public class McuServiceImpl implements McuService {

    @Autowired
    private UmsMeetingPlatformMapper mapper;

    @Autowired
    private McuUrlFactory factory;

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private McuConvert convert;

    @Override
    public BaseResult login(McuRequestDTO request) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(request.getMcuId());
        log.info("mcu登录平台入参信息:{};entity:{}", request, entity);
        McuLoginRequest mcuLoginRequest = convert.login(entity);
        String url = factory.geturl(entity.getMcutype());
        String response = template.postForObject(url + "/login/" + System.currentTimeMillis(), JSON.toJSONString(mcuLoginRequest), String.class);

        McuLoginResponse mcuLoginResponse = JSON.parseObject(response, McuLoginResponse.class);
        log.info("mcu登录平台中间件应答:{};转换:{}", response, mcuLoginResponse);
        String errorMsg = "mcu登录平台失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_ERROR, mcuLoginResponse);
        McuLoginVO loginVO = new McuLoginVO();
        loginVO.setSsid(mcuLoginResponse.getSsid());
        return BaseResult.succeed(loginVO);
    }
}

