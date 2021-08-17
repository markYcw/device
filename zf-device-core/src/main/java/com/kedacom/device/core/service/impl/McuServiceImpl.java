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
import com.kedacom.device.mp.mcu.request.*;
import com.kedacom.device.mp.mcu.response.*;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.*;
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
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu获取会议信息:{};entity:{}", dto, entity);

        McuConfInfoRequest request = convert.confinfo(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/confinfo/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        McuConfInfoResponse response = JSON.parseObject(string, McuConfInfoResponse.class);

        log.info("mcu获取会议信息中间件应答:{}", response);
        String errorMsg = "mcu获取会议信息失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        McuConfInfoVO vo = convert.confinfoRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult conf(McuConfDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu创建/删除会议:{};entity:{}", dto, entity);

        McuConfRequest request = convert.conf(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/conf/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        McuConfResponse response = JSON.parseObject(string, McuConfResponse.class);

        log.info("mcu创建/删除会议中间件应答:{}", response);
        String errorMsg = "mcu创建/删除会议失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        McuConfVO vo = convert.confRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult confTemplate(McuConfTemplateDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu开启会议模板:{};entity:{}", dto, entity);

        McuConfTemplateRequest request = convert.confTemplate(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/conftemplate/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu开启会议模板中间件应答:{}", response);
        String errorMsg = "mcu开启会议模板失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("开启成功");
    }

    @Override
    public BaseResult mtMembers(McuMtMembersDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu获取与会成员:{};entity:{}", dto, entity);

        McuMtMembersRequest request = convert.mtMembers(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/mtmembers/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        McuMtmembersResponse response = JSON.parseObject(string, McuMtmembersResponse.class);

        log.info("mcu获取与会成员中间件应答:{}", response);
        String errorMsg = "mcu获取与会成员失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        McuMtmembersVO vo = convert.mtMembersRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult mt(McuMtDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu添加/删除终端:{};entity:{}", dto, entity);

        McuMtRequest request = convert.mt(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/mt/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu添加/删除终端中间件应答:{}", response);
        String errorMsg = "mcu添加/删除终端失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult mtCall(McuMtCallDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu呼叫/挂断终端:{};entity:{}", dto, entity);

        McuMtCallRequest request = convert.mtCall(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/mtcall/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu呼叫/挂断终端中间件应答:{}", response);
        String errorMsg = "mcu呼叫/挂断终端失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult speaker(McuSpeakerDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu设置/取消发言人:{};entity:{}", dto, entity);

        McuSpeakerRequest request = convert.speaker(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/speaker/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu设置/取消发言人中间件应答:{}", response);
        String errorMsg = "mcu设置/取消发言人失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult chairman(McuChairmanDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu设置/取消主席:{};entity:{}", dto, entity);

        McuChairmanRequest request = convert.chairman(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/chairman/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu设置/取消主席中间件应答:{}", response);
        String errorMsg = "mcu设置/取消主席失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult silence(McuSilenceDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu静音:{};entity:{}", dto, entity);

        McuSilenceRequest request = convert.silence(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/silence/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu静音中间件应答:{}", response);
        String errorMsg = "mcu静音失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult mute(McuMuteDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu哑音:{};entity:{}", dto, entity);

        McuMuteRequest request = convert.mute(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/mute/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu哑音中间件应答:{}", response);
        String errorMsg = "mcu哑音失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult volume(McuVolumeDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu调节音量:{};entity:{}", dto, entity);

        McuVolumeRequest request = convert.volume(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/volume/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu调节音量中间件应答:{}", response);
        String errorMsg = "mcu调节音量失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult dual(McuDualDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu终端双流控制:{};entity:{}", dto, entity);

        McuDualRequest request = convert.dual(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/dual/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu终端双流控制中间件应答:{}", response);
        String errorMsg = "mcu终端双流控制音量失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult videoMix(McuVideoMixDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu开始/停止画面合成:{};entity:{}", dto, entity);

        McuVideoMixRequest request = convert.videoMix(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/videomix/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu开始/停止画面合成中间件应答:{}", response);
        String errorMsg = "mcu开始/停止画面合成失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult intaudioMix(McuIntaudioMixDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu智能混音:{};entity:{}", dto, entity);

        McuIntaudioMixRequest request = convert.intaudioMix(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/intaudiomix/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu智能混音中间件应答:{}", response);
        String errorMsg = "mcu智能混音失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult audioMix(McuAudioMixDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu开始/停止混音:{};entity:{}", dto, entity);

        McuAudioMixRequest request = convert.audioMix(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/audiomix/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu开始/停止混音中间件应答:{}", response);
        String errorMsg = "mcu开始/停止混音失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult audioMixMember(McuAudioMixMemberDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu添加/删除混音成员:{};entity:{}", dto, entity);

        McuAudioMixMemberRequest request = convert.audioMixMember(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/mtmembers/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu添加/删除混音成员中间件应答:{}", response);
        String errorMsg = "mcu添加/删除混音成员失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult rec(McuRecDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu开始/暂停/恢复/停止录像:{};entity:{}", dto, entity);

        McuRecRequest request = convert.rec(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/rec/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        McuRecResponse response = JSON.parseObject(string, McuRecResponse.class);

        log.info("mcu开始/暂停/恢复/停止录像中间件应答:{}", response);
        String errorMsg = "mcu开始/暂停/恢复/停止录像失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        McuRecVO vo = convert.recRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult tvWalls(McuRequestDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu获取电视墙列表:{};entity:{}", dto, entity);

        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/tvwalls/{ssid}/{ssno}", null, String.class, param.getParamMap());
        McuTvWallsResponse response = JSON.parseObject(string, McuTvWallsResponse.class);

        log.info("mcu获取电视墙列表中间件应答:{}", response);
        String errorMsg = "mcu获取电视墙列表失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        McuTvWallsVO vo = convert.tvWallsRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult tvwall(McuTvWallDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu开始/停止上电视墙:{};entity:{}", dto, entity);

        McuTvWallRequest request = convert.tvwall(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/tvwall/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu开始/停止上电视墙中间件响应:{}", response);
        String errorMsg = "mcu开始/停止上电视墙失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult exchange(McuExchangeDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu开始/停止码流交换:{};entity:{}", dto, entity);

        McuExchangeRequest request = convert.exchange(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/exchange/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu开始/停止码流交换中间件响应:{}", response);
        String errorMsg = "mcu开始/停止码流交换失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult message(McuMessageDTO dto) {
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMeetingPlatformEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        log.info("mcu发送短消息:{};entity:{}", dto, entity);

        McuMessageRequest request = convert.message(dto);
        McuBasicParam param = tool.getParam(entity);
        String string = template.postForObject(param.getUrl() + "/message/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        MpResponse response = JSON.parseObject(string, MpResponse.class);

        log.info("mcu发送短消息中间件响应:{}", response);
        String errorMsg = "mcu发送短消息失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_OPERATE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

}

