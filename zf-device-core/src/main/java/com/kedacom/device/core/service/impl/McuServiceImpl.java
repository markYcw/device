package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.McuConvert;
import com.kedacom.device.core.entity.McuBasicParam;
import com.kedacom.device.core.exception.MpException;
import com.kedacom.device.core.mapper.UmsMcuMapper;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.utils.*;
import com.kedacom.device.mp.MpResponse;
import com.kedacom.device.mp.mcu.request.*;
import com.kedacom.device.mp.mcu.response.*;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.*;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.*;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hxj ycw
 * @date: 2021/8/13 13:50
 * @description 会议平台业务类
 */
@Slf4j
@Service(value = "mcuService")
public class McuServiceImpl implements McuService {

    @Resource
    private UmsMcuMapper mapper;

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

    @Value("${zf.mcuNtyUrl.server_addr:127.0.0.1:9000}")
    private String mcuNtyUrl;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/umsMcu/mcuNotify";

    //mcu状态池 若成功登录则把数据库ID和登录状态放入此池中1为已登录，若登出则从此状态池中移除
    public static ConcurrentHashMap<Long,Integer> mcuStatusPoll = new ConcurrentHashMap<>();

    @Override
    public BaseResult<McuLoginVO> login(McuRequestDTO dto) {
        log.info("mcu登录平台入参信息:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        if (ObjectUtil.isNull(entity)) {
            throw new MpException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        McuLoginRequest request = convert.login(entity);
        String ntyUrl = REQUEST_HEAD + mcuNtyUrl + NOTIFY_URL;
        request.setNtyUrl(ntyUrl);
        //设置mcu类型目前只考虑5.0所以暂时 type为1
        request.setType(DevTypeConstant.updateRecordKey);
        String url = factory.geturl(entity.getDevtype());
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());

        log.info("mcu登录平台中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        log.info("mcu登录平台中间件应答:{}", string);

        McuLoginResponse response = JSON.parseObject(string, McuLoginResponse.class);
        String errorMsg = "mcu登录平台失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_LOGIN_FAILED, response);
        entity.setSsid(response.getSsid());
        entity.setModifyTime(new Date());
        mapper.updateById(entity);

        McuLoginVO loginVO = new McuLoginVO();
        loginVO.setSsid(response.getSsid());
        //往mcu状态池放入当前mcu状态 1已登录
        mcuStatusPoll.put(dto.getMcuId(),DevTypeConstant.updateRecordKey);
        return BaseResult.succeed(loginVO);
    }

    @Override
    public BaseResult logout(McuRequestDTO dto) {
        log.info("mcu登出平台入参信息:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);

        ResponseEntity<String> responseEntity = template.exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
        String string = responseEntity.getBody();
        log.info("mcu登出平台中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu登出平台失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_LOGOUT_FAILED, response);

        LambdaUpdateWrapper<UmsMcuEntity> wrapper = new LambdaUpdateWrapper();
        wrapper.set(UmsMcuEntity::getSsid, null)
                .set(UmsMcuEntity::getModifyTime, new Date())
                .eq(UmsMcuEntity::getId, dto.getMcuId());
        mapper.update(null, wrapper);
        //往mcu状态池移除当前mcu的id
        mcuStatusPoll.remove(dto.getMcuId());
        return BaseResult.succeed("登出成功");
    }

    @Override
    public BaseResult<AccountVo> account(McuAccountDTO dto) {
        log.info("mcu创建/删除账户:{}", dto);
        AccountInfo accountInfo = dto.getAccountInfo();
        if(!ObjectUtil.isNull(accountInfo)){
            List<DepartmentInfo> departments = accountInfo.getDepartments();
            if(!CollectionUtil.isEmpty(departments)){
                for (DepartmentInfo department : departments) {
                    if(department.getDepartmentMoId()==null||department.getDepartmentMoId().equals("")){
                        return BaseResult.failed("部门序号不能为空！，请检查");
                    }
                }
            }
        }
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuAccountRequest request = convert.account(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu创建/删除账户中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/account/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu创建账号中间件应答:{}", string);

        AccountResponse response = JSON.parseObject(string, AccountResponse.class);
        String errorMsg = "mcu创建账号失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_ACCOUNT_FAILED, response);
        AccountVo vo = convert.convertToAccountVo(response);
        return BaseResult.succeed("操作成功",vo);
    }

    @Override
    public BaseResult<McuConfsVO> confs(McuConfsDTO dto) {
        log.info("mcu获取会议列表:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuConfsRequest request = convert.confs(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu获取会议列表中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/confs/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu获取会议列表中间件应答:{}", string);

        McuConfsResponse response = JSON.parseObject(string, McuConfsResponse.class);
        String errorMsg = "mcu获取会议列表失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_CONF_S_FAILED, response);
        McuConfsVO vo = convert.accountRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<McuConfTemplateVO> templates(McuTemplatesDTO dto) {
        log.info("mcu获取会议模板列表:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuTemplatesRequest request = convert.templates(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu获取会议模板列表中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/templates/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu获取会议模板列表中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu获取会议模板列表失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_TEMPLATES_FAILED, response);
        McuConfTemplateVO vo = JSON.parseObject(string, McuConfTemplateVO.class);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<McuConfInfoVO> confinfo(McuConfInfoDTO dto) {
        log.info("mcu获取会议信息:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuConfInfoRequest request = convert.confinfo(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu获取会议信息中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/confinfo/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu获取会议信息中间件应答:{}", string);

        McuConfInfoResponse response = JSON.parseObject(string, McuConfInfoResponse.class);
        String errorMsg = "mcu获取会议信息失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_CONF_INFO_FAILED, response);
        McuConfInfoVO vo = convert.confinfoRes(response);
        ConfInfo confInfo = vo.getConfInfo();
        String startTime = confInfo.getStartTime();
        String endTime = confInfo.getEndTime();
        String dateStrFromISO8601Timestamp = DateUtils.getDateStrFromISO8601Timestamp(startTime);
        String dateStrFromISO8601Timestamp1 = DateUtils.getDateStrFromISO8601Timestamp(endTime);
        confInfo.setStartTime(dateStrFromISO8601Timestamp);
        confInfo.setEndTime(dateStrFromISO8601Timestamp1);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<McuConfVO> conf(McuConfDTO dto) {
        log.info("mcu创建/删除会议:{}", dto);
        if(!ObjectUtil.isNull(dto.getConfInfo())){
            List<VideoFormat> videoFormats = dto.getConfInfo().getVideoFormats();
            if(CollectionUtil.isEmpty(videoFormats)){
                return BaseResult.failed("主视频格式列表 不能为空");
            }
        }
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuConfRequest request = convert.conf(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu创建/删除会议中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/conf/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu创建/删除会议中间件应答:{}", string);

        McuConfResponse response = JSON.parseObject(string, McuConfResponse.class);
        String errorMsg = "mcu创建/删除会议失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_CONF_FAILED, response);
        McuConfVO vo = convert.confRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult confTemplate(McuConfTemplateDTO dto) {
        log.info("mcu开启会议模板:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuConfTemplateRequest request = convert.confTemplate(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu开启会议模板中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/conftemplate/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu开启会议模板中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu开启会议模板失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_CONF_TEMPLATE_FAILED, response);
        return BaseResult.succeed("开启成功");
    }

    @Override
    public BaseResult<McuMtmembersVO> mtMembers(McuMtMembersDTO dto) {
        log.info("mcu获取与会成员:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuMtMembersRequest request = convert.mtMembers(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu获取与会成员中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/mtmembers/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu获取与会成员中间件应答:{}", string);

        McuMtmembersResponse response = JSON.parseObject(string, McuMtmembersResponse.class);
        String errorMsg = "mcu获取与会成员失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_MT_MEMBERS_FAILED, response);

        McuMtmembersVO vo = convert.mtMembersRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<McuMtVO> mt(McuMtDTO dto) {
        log.info("mcu添加/删除终端:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuMtRequest request = convert.mt(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu添加/删除终端中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/mt/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu添加/删除终端中间件应答:{}", string);

        McuMtResponse response = JSON.parseObject(string, McuMtResponse.class);
        String errorMsg = "mcu添加/删除终端失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_MT_FAILED, response);
        McuMtVO vo = convert.mtRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult mtCall(McuMtCallDTO dto) {
        log.info("mcu呼叫/挂断终端:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuMtCallRequest request = convert.mtCall(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu呼叫/挂断终端中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/mtcall/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu呼叫/挂断终端中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu呼叫/挂断终端失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_MT_CALL_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult speaker(McuSpeakerDTO dto) {
        log.info("mcu设置/取消发言人:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuSpeakerRequest request = convert.speaker(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu设置/取消发言人中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/speaker/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu设置/取消发言人中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu设置/取消发言人失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_SPEAKER_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult chairman(McuChairmanDTO dto) {
        log.info("mcu设置/取消主席:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuChairmanRequest request = convert.chairman(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu设置/取消主席中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/chairman/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu设置/取消主席中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu设置/取消主席失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_CHAIRMAN_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult silence(McuSilenceDTO dto) {
        log.info("mcu静音:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuSilenceRequest request = convert.silence(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu静音中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/silence/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu静音中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu静音失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_SILENCE_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult mute(McuMuteDTO dto) {
        log.info("mcu哑音:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuMuteRequest request = convert.mute(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu哑音中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/mute/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu哑音中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu哑音失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_MUTE_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult volume(McuVolumeDTO dto) {
        log.info("mcu调节音量:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuVolumeRequest request = convert.volume(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu调节音量中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/volume/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu调节音量中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu调节音量失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_VOLUME_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult dual(McuDualDTO dto) {
        log.info("mcu终端双流控制:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuDualRequest request = convert.dual(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu终端双流控制中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/dual/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu终端双流控制中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu终端双流控制失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_DUAL_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult videoMix(McuVideoMixDTO dto) {
        log.info("mcu开始/停止画面合成:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuVideoMixRequest request = convert.videoMix(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu开始/停止画面合成中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/videomix/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu开始/停止画面合成中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu开始/停止画面合成失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_VIDEO_MIX_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult audioMix(McuAudioMixDTO dto) {
        log.info("mcu开始/停止混音:{}", dto);
        if(dto.getType()==0||dto.getMode()==2){
            if(CollectionUtil.isEmpty(dto.getMtInfos())){
                return BaseResult.failed("开始混音时终端ID为必填！");
            }
        }
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuAudioMixRequest request = convert.audioMix(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu开始/停止混音中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/audiomix/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu开始/停止混音中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu开始/停止混音失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_AUDIO_MIX_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult<String> audioMixMember(McuAudioMixMemberDTO dto) {
        log.info("mcu添加/删除混音成员:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuAudioMixMemberRequest request = convert.audioMixMember(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu添加/删除混音成员中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/audiomixmember/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu添加/删除混音成员中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu添加/删除混音成员失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_AUDIO_MIX_MEMBER_FAILED, response);
        return BaseResult.succeed(string);
    }

    @Override
    public BaseResult<McuRecVO> rec(McuRecDTO dto) {
        log.info("mcu开始/暂停/恢复/停止录像:{}", dto);
        if(dto.getType()==0){
            if(ObjectUtil.isNull(dto.getRecParam())){
                return BaseResult.failed("开始录像时录像参数必填！！！");
            }
        }
        if(dto.getType()!=0){
            dto.setRecParam(null);
        }
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuRecRequest request = convert.rec(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu开始/暂停/恢复/停止录像中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/rec/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu开始/暂停/恢复/停止录像中间件应答:{}", string);

        McuRecResponse response = JSON.parseObject(string, McuRecResponse.class);
        String errorMsg = "mcu开始/暂停/恢复/停止录像失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_REC_FAILED, response);

        McuRecVO vo = convert.recRes(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<McuTvWallsVO> tvWalls(McuRequestDTO dto) {
        log.info("mcu获取电视墙列表:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);

        String string = template.getForObject(param.getUrl() + "/hdus/{ssid}/{ssno}", String.class, param.getParamMap());
        log.info("mcu获取电视墙列表中间件应答:{}", string);

        McuTvWallsResponse response = JSON.parseObject(string, McuTvWallsResponse.class);
        String errorMsg = "mcu获取电视墙列表失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_TV_WALLS_FAILED, response);
        McuTvWallsVO vo = convert.convertMcuTvWallsVO(response);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult tvwall(McuTvWallDTO dto) {
        log.info("mcu开始/停止上电视墙:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuTvWallRequest request = convert.convertTvWall(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu开始/停止上电视墙中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/hdu/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu开始/停止上电视墙中间件响应:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu开始/停止上电视墙失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_TV_WALL_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult exchange(McuExchangeDTO dto) {
        log.info("mcu开始/停止码流交换:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuExchangeRequest request = convert.exchange(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu开始/停止码流交换中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/exchange/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu开始/停止码流交换中间件响应:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu开始/停止码流交换失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_EXCHANGE_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult message(McuMessageDTO dto) {
        log.info("mcu发送短消息:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuMessageRequest request = convert.message(dto);
        McuBasicParam param = tool.getParam(entity);

        log.info("mcu发送短消息中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(param.getUrl() + "/message/{ssid}/{ssno}", JSON.toJSONString(request), String.class, param.getParamMap());
        log.info("mcu发送短消息中间件响应:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu发送短消息失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_MESSAGE_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult<String> hb(McuRequestDTO dto) {
        log.info("mcu发送心跳:{}", dto);
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("mcu发送心跳响应:{}", exchange.getBody());
        String errorMsg = "mcu发送心跳失败:{},{},{}";
        MpResponse response = JSON.parseObject(exchange.getBody(), MpResponse.class);
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_HB_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult<ConfTemplateVo> confTemplates(ConfTemplateDTO dto) {
        log.info("mcu创建/删除会议模板:{}", dto);
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);
        log.info("mcu创建/删除会议模板中间件入参:{}",JSON.toJSONString(dto));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/template/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("mcu创建/删除会议模板响应:{}", s);
        String errorMsg = "mcu创建/删除会议模板失败:{},{},{}";
        ConfTemplateResponse response = JSON.parseObject(s, ConfTemplateResponse.class);
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_CONF_TEMPLATE_S_FAILED, response);
        ConfTemplateVo vo = convert.convertToConfTemplateVo(response);
        return BaseResult.succeed("mcu创建/删除会议模板成功",vo);
    }

    @Override
    public BaseResult<ConfTemplateInfoVo> templateInfo(GetConfTemplateDTO dto) {
        log.info("mcu4.8获取会议模板信息:{}", dto);
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);
        log.info("mcu获取会议模板信息中间件入参:{}",JSON.toJSONString(dto));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/templateinfo/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("mcu获取会议模板信息响应:{}", s);
        String errorMsg = "mcu获取会议模板信息失败:{},{},{}";
        MpResponse response = JSON.parseObject(s, MpResponse.class);
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_HB_FAILED, response);
        ConfTemplateInfoVo vo = JSON.parseObject(s, ConfTemplateInfoVo.class);
        return BaseResult.succeed("mcu获取会议模板信息成功",vo);
    }

    @Override
    public BaseResult<AccountsVo> accounts(AccountsDto dto) {
        log.info("4.4查询所有账户:{}", dto);
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);
        log.info("mcu查询所有账户中间件入参:{}",JSON.toJSONString(dto));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/accounts/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("mcu查询所有账户响应:{}", s);
        String errorMsg = "mcu查询所有账户失败:{},{},{}";
        MpResponse response = JSON.parseObject(s, MpResponse.class);
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_HB_FAILED, response);
        AccountsVo vo = JSON.parseObject(s, AccountsVo.class);
        List<AccountInfoMessage> accountInfo = vo.getAccountInfo();
        for (AccountInfoMessage accountInfoMessage : accountInfo) {
            accountInfoMessage.setDateOfBirth(DateUtils.getDateStrFromISO8601Timestamp(accountInfoMessage.getDateOfBirth()));
        }
        vo.setAccountInfo(accountInfo);
        return BaseResult.succeed("mcu查询所有账户成功",vo);
    }

    @Override
    public BaseResult<McuRecStatusVO> recState(McuRecStatusDTO dto) {
        log.info("获取录像状态接口入参:{}", dto);
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);
        log.info("mcu获取录像状态中间件入参:{}",JSON.toJSONString(dto));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/recstate/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("mcu获取录像状态响应:{}", s);
        String errorMsg = "mcu获取录像状态失败:{},{},{}";
        MpResponse response = JSON.parseObject(s, MpResponse.class);
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_REC_STATUS_FAILED, response);
        McuRecStatusVO vo = JSON.parseObject(s, McuRecStatusVO.class);
        return BaseResult.succeed("mcu获取录像状态成功",vo);
    }

    @Override
    public BaseResult<DepartmentsVO> departments(McuRequestDTO dto) {
        log.info("查询所有部门接口入参:{}", dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);

        String string = template.getForObject(param.getUrl() + "/ departments/{ssid}/{ssno}", String.class, param.getParamMap());
        log.info("mcu查询所有部门中间件应答:{}", string);

        MpResponse response = JSON.parseObject(string, MpResponse.class);
        String errorMsg = "mcu查询所有部门失败:{},{},{}";
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_DEPARTMENTS_FAILED, response);
        DepartmentsVO vo = JSON.parseObject(string, DepartmentsVO.class);
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<DepartmentVO> department(DepartmentDTO dto) {
        log.info("创建/删除部门接口入参:{}", dto);
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);
        log.info("mcu创建/删除部门中间件入参:{}",JSON.toJSONString(dto));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/department/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("mcu创建/删除部门响应:{}", s);
        String errorMsg = "mcu创建/删除部门失败:{},{},{}";
        MpResponse response = JSON.parseObject(s, MpResponse.class);
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_REC_STATUS_FAILED, response);
        DepartmentVO vo = JSON.parseObject(s, DepartmentVO.class);
        return BaseResult.succeed("mcu创建/删除部门成功",vo);
    }

    @Override
    public BaseResult<RecsVO> recs(RecsDTO dto) {
        log.info("获取录像列表接口入参:{}", dto);
        UmsMcuEntity entity = mapper.selectById(dto.getMcuId());
        responseUtil.handleMp(entity);
        McuBasicParam param = tool.getParam(entity);
        log.info("mcu获取录像列表中间件入参:{}",JSON.toJSONString(dto));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/recs/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("mcu获取录像列表响应:{}", s);
        String errorMsg = "mcu获取录像列表失败:{},{},{}";
        MpResponse response = JSON.parseObject(s, MpResponse.class);
        responseUtil.handleMpRes(errorMsg, DeviceErrorEnum.MCU_REC_STATUS_FAILED, response);
        RecsVO vo = JSON.parseObject(s, RecsVO.class);
        return BaseResult.succeed("mcu获取录像列表成功",vo);
    }

}

