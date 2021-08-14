package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.kedacom.BaseResult;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.McuConvert;
import com.kedacom.device.core.entity.McuBasicParam;
import com.kedacom.device.core.mapper.UmsMeetingPlatformMapper;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.McuBasicTool;
import com.kedacom.device.core.utils.McuUrlFactory;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import com.kedacom.device.meetingPlatform.MpResponse;
import com.kedacom.device.meetingPlatform.mcu.request.McuAccountRequest;
import com.kedacom.device.meetingPlatform.mcu.request.McuConfsRequest;
import com.kedacom.device.meetingPlatform.mcu.request.McuLoginRequest;
import com.kedacom.device.meetingPlatform.mcu.response.McuConfsResponse;
import com.kedacom.device.meetingPlatform.mcu.response.McuLoginResponse;
import com.kedacom.meeting.mcu.McuRequestDTO;
import com.kedacom.meeting.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.meeting.mcu.request.McuAccountDTO;
import com.kedacom.meeting.mcu.request.McuConfsDTO;
import com.kedacom.meeting.mcu.response.McuConfsVO;
import com.kedacom.meeting.mcu.response.McuLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hxj
 * @date: 2021/8/13 13:50
 * @description 会议平台业务类
 */
@Service(value = "mcuService")
@Slf4j
public class McuServiceImpl implements McuService {

    @Resource
    private UmsMeetingPlatformMapper mapper;

    @Autowired
    private McuUrlFactory factory;

    @Autowired
    private McuBasicTool tool;

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private McuConvert convert;

    @Override
    public BaseResult login(McuRequestDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu登录平台入参信息:{};entity:{};", dto, entity);
        McuLoginRequest request = convert.login(entity);

        String url = factory.geturl(entity.getMcutype());
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", System.currentTimeMillis());
        String string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        McuLoginResponse response = JSON.parseObject(string, McuLoginResponse.class);

        log.info("mcu登录平台中间件应答:{}", string);
        String errorMsg = "mcu登录平台失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);
        entity.setSsid(response.getSsid());
        mapper.updateById(entity);

        McuLoginVO loginVO = new McuLoginVO();
        loginVO.setSsid(response.getSsid());
        return BaseResult.succeed(loginVO);
    }

    @Override
    public BaseResult logout(McuRequestDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu登出平台入参信息:{};entity:{}", dto, entity);

        McuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> responseEntity = template.exchange(param.getUrl() + "/logout/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
        String string = responseEntity.getBody();
        MpResponse response = JSON.parseObject(string, MpResponse.class);
        log.info("mcu登录平台中间件应答:{};转换:{}", string, response);
        String errorMsg = "mcu登出平台失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);
        return BaseResult.succeed("登出成功");
    }

    @Override
    public BaseResult account(McuAccountDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu创建/删除账户:{};entity:{}", dto, entity);

        McuAccountRequest request = convert.account(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/account/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu创建账号中间件应答:{}", response);
        String errorMsg = "mcu创建账号失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult confs(McuConfsDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu获取会议列表:{};entity:{}", dto, entity);

        McuConfsRequest request = convert.confs(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/confs/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        McuConfsResponse response = JSON.parseObject(string, McuConfsResponse.class);

        log.info("mcu获取会议列表中间件应答:{}", response);
        String errorMsg = "mcu获取会议列表失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        McuConfsVO vo = convert.accountRes(response);
        return BaseResult.succeed(vo);
    }
}

