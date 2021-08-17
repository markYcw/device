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
import com.kedacom.device.mp.MpResponse;
import com.kedacom.device.mp.mcu.request.McuAccountRequest;
import com.kedacom.device.mp.mcu.request.McuConfsRequest;
import com.kedacom.device.mp.mcu.request.McuLoginRequest;
import com.kedacom.device.mp.mcu.request.McuTemplatesRequest;
import com.kedacom.device.mp.mcu.response.McuConfsResponse;
import com.kedacom.device.mp.mcu.response.McuLoginResponse;
import com.kedacom.device.mp.mcu.response.McuTemplatesResponse;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.McuConfsVO;
import com.kedacom.mp.mcu.response.McuLoginVO;
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

//    @Value("${zf.mcuNtyUrl.server_addr}")
//    private String mcuNtyUrl;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/mp/notify";

    @Override
    public BaseResult login(McuRequestDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu登录平台入参信息:{};entity:{};", dto, entity);
        McuLoginRequest request = convert.login(entity);
//
//        String ntyUrl = REQUEST_HEAD + mcuNtyUrl + NOTIFY_URL;
//        request.setNtyUrl(ntyUrl);

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

    @Override
    public BaseResult templates(McuTemplatesDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu获取会议模板列表:{};entity:{}", dto, entity);

        McuTemplatesRequest request = convert.templates(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/confs/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        McuTemplatesResponse response = JSON.parseObject(string, McuTemplatesResponse.class);

        log.info("mcu获取会议模板列表中间件应答:{}", response);
        String errorMsg = "mcu获取会议模板列表失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        McuConfsVO vo = convert.templatesRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult confinfo(McuConfInfoDTO dto) {
        return null;
    }

    @Override
    public BaseResult conf(McuConfDTO dto) {
        return null;
    }

    @Override
    public BaseResult conftemplate(McuConfTemplateDTO dto) {
        return null;
    }

    @Override
    public BaseResult mtMembers(McuMtMembersDTO dto) {
        return null;
    }

    @Override
    public BaseResult mt(McuMtDTO dto) {
        return null;
    }

    @Override
    public BaseResult mtCall(McuMtCallDTO dto) {
        return null;
    }

    @Override
    public BaseResult speaker(McuSpeakerDTO dto) {
        return null;
    }

    @Override
    public BaseResult chairman(McuChairmanDTO dto) {
        return null;
    }

    @Override
    public BaseResult silence(McuSilenceDTO dto) {
        return null;
    }

    @Override
    public BaseResult mute(McuMuteDTO dto) {
        return null;
    }

    @Override
    public BaseResult volume(McuVolumeDTO dto) {
        return null;
    }

    @Override
    public BaseResult dual(McuDualDTO dto) {
        return null;
    }

    @Override
    public BaseResult videoMix(McuVideoMixDTO dto) {
        return null;
    }

    @Override
    public BaseResult intaudioMix(McuIntaudioMixDTO dto) {
        return null;
    }

    @Override
    public BaseResult audioMix(McuAudioMixDTO dto) {
        return null;
    }

    @Override
    public BaseResult audioMixMember(McuAudioMixMemberDTO dto) {
        return null;
    }

    @Override
    public BaseResult rec(McuRecDTO dto) {
        return null;
    }

    @Override
    public BaseResult tvWalls(McuRequestDTO dto) {
        return null;
    }

    @Override
    public BaseResult tvwall(McuTvWallDTO dto) {
        return null;
    }

    @Override
    public BaseResult message(McuMessageDTO dto) {
        return null;
    }
}

