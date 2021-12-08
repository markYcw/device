package com.kedacom.device.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.MtConvert;
import com.kedacom.device.core.entity.MtEntity;
import com.kedacom.device.core.entity.MtTypeEntity;
import com.kedacom.device.core.exception.MtException;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.mapper.MtMapper;
import com.kedacom.device.core.mapper.MtTypeMapper;
import com.kedacom.device.core.notify.stragegy.NotifyHandler;
import com.kedacom.device.core.service.MtService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.MtUrlFactory;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import com.kedacom.mt.*;
import com.kedacom.mt.response.GetMtStatusResponseVo;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/30
 */
@Slf4j
@Service
public class MtServiceImpl implements MtService {

    @Resource
    MtMapper mtMapper;

    @Resource
    CuMapper cuMapper;

    @Resource
    MtTypeMapper mtTypeMapper;

    @Resource
    MtUrlFactory mtUrlFactory;

    @Resource
    HandleResponseUtil responseUtil;

    @Resource
    RemoteRestTemplate remoteRestTemplate;

    private final static String NTY_URL = "http://127.0.0.1:9000/api/api-device/ums/mt/mtNotify";

    public static Set<Integer> synHashSet = Collections.synchronizedSet(new HashSet<Integer>());

    @Override
    public Page<TerminalVo> mtPage(TerminalQuery terminalQuery) {

        log.info("分页查询终端信息入参TerminalQuery : {}", terminalQuery);
        Page<MtEntity> terminalEntityPage = new Page<>(terminalQuery.getPage(), terminalQuery.getLimit());
        LambdaQueryWrapper<MtEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(terminalQuery.getName())) {
            wrapper.like(MtEntity::getName, terminalQuery.getName());
        }
        if (StrUtil.isNotBlank(terminalQuery.getIp())) {
            wrapper.like(MtEntity::getIp, terminalQuery.getIp());
        }
        Page<MtEntity> page = mtMapper.selectPage(terminalEntityPage, wrapper);
        List<MtEntity> records = page.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            log.error("分页查询终端信息为空");
            return null;
        }
        List<TerminalVo> terminalVoList = queryConnectionStatus(records);
        Page<TerminalVo> resultPage = new Page<>();
        BeanUtil.copyProperties(page, resultPage);
        resultPage.setRecords(terminalVoList);

        return resultPage;
    }

    public List<TerminalVo> queryConnectionStatus(List<MtEntity> records) {

        List<TerminalVo> terminalVoList = MtConvert.INSTANCE.convertTerminalVoList(records);

        if (CollectionUtil.isEmpty(synHashSet)) {
            return terminalVoList;
        }
        out : for (Integer dbId : synHashSet) {
            for (TerminalVo terminalVo : terminalVoList) {
                if (dbId.equals(terminalVo.getId())) {
                    terminalVo.setStatus(1);
                    continue out;
                }
            }
        }

        return terminalVoList;
    }

    @Override
    public TerminalVo queryMt(Integer dbId) {

        log.info("查询终端信息请求参数 : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        List<MtEntity> entities = new ArrayList<>();
        entities.add(entity);
        List<TerminalVo> terminalVoList = queryConnectionStatus(entities);

        return terminalVoList.get(0);
    }

    @Override
    public List<Integer> queryMtIds() {

        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(MtEntity::getMtid);
        List<MtEntity> mtEntities = mtMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(mtEntities)) {
            log.error("查询mtId不为空的终端集合为空");
            return null;
        }

        return mtEntities.stream().map(MtEntity::getId).collect(Collectors.toList());
    }

    @Override
    public boolean isRepeatIp(TerminalVo terminalVo) {

        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MtEntity::getIp, terminalVo.getIp());
        if (terminalVo.getId() != null) {
            queryWrapper.ne(MtEntity::getId, terminalVo.getId());
        }
        MtEntity entity = mtMapper.selectOne(queryWrapper);

        return entity != null;
    }

    @Override
    public boolean isRepeatName(TerminalVo terminalVo) {

        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MtEntity::getName, terminalVo.getName());
        if (terminalVo.getId() != null) {
            queryWrapper.ne(MtEntity::getId, terminalVo.getId());
        }
        MtEntity entity = mtMapper.selectOne(queryWrapper);

        return entity != null;
    }

    @Override
    public TerminalVo saveMt(TerminalVo terminalVo) {

        log.info("保存终端信息请求参数 : {}", terminalVo);
        MtEntity entity = MtConvert.INSTANCE.convertMtEntity(terminalVo);
        mtMapper.insert(entity);
        terminalVo.setId(entity.getId());

        return terminalVo;
    }

    @Override
    public boolean updateMt(TerminalVo terminalVo) {

        log.info("修改终端信息请求参数 : {}", terminalVo);
        MtEntity entity = MtConvert.INSTANCE.convertMtEntity(terminalVo);

        return mtMapper.updateById(entity) > 0;
    }

    @Override
    public boolean deleteMt(List<Integer> dbIds) {

        log.info("删除终端信息请求参数 : {}", dbIds);
        int batchIds = mtMapper.deleteBatchIds(dbIds);

        return batchIds == dbIds.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer loginById(Integer dbId) {

        log.info("登录终端请求参数 : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        if (entity == null) {
            throw new MtException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        LoginParamVo loginParamVo = MtConvert.INSTANCE.convertLoginParamVo(entity);
        loginParamVo.setNtyUrl(NTY_URL);
        Map<String, Long> paramMap = setParamMap(null);

        log.info("远端登录终端请求参数 loginParamVo : {}", loginParamVo);
        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/login/{ssno}", JSON.toJSONString(loginParamVo), String.class, paramMap);
        log.info("远端登录终端响应参数 response : {}", response);
        MtLoginResponse mtLoginResponse = JSON.parseObject(response, MtLoginResponse.class);
        String errorMsg = "终端登录失败 : {}, {}, {}";
        assert mtLoginResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_LOGIN_FAILED, mtLoginResponse);
        entity.setMtid(mtLoginResponse.getSsid());
        mtMapper.updateById(entity);

        // 将登录的终端id添加到维护终端心跳的缓存中
        synHashSet.add(dbId);
        log.info("终端 : {}, 添加到缓存中 synHashSet : {}", dbId, synHashSet);

        return mtLoginResponse.getSsid();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logOutById(Integer dbId) {

        log.info("退出登录终端信息请求参数 : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();

        log.info("远端退出登录终端请求参数 ssid : {}", entity.getMtid());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate()
                .exchange(mtRequestUrl + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, paramMap);
        log.info("远端退出登录终端响应参数 exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "退出终端登录失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_LOGOUT_FAILED, mtResponse);
        entity.setMtid(null);

        // 将退出登录的终端id从维护终端心跳的缓存中删除
        synHashSet.remove(dbId);

        return mtMapper.updateById(entity) > 0;
    }

    @Override
    public boolean heartBeat(Integer dbId) {

        log.info("发送心跳请求参数 : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();

        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate()
                .exchange(mtRequestUrl + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("发送心跳响应参数 exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "发送心跳失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_HEART_BEAT_FAILED, mtResponse);

        return true;
    }

    @Override
    public Integer getMtType(Integer dbId) {

        log.info("获取终端类型请求参数 : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);

        LambdaQueryWrapper<MtTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MtTypeEntity::getMtName, entity.getMtname());
        MtTypeEntity selectOne = mtTypeMapper.selectOne(queryWrapper);
        if (selectOne == null) {
            log.error("该终端 : {} 不存在定义的类型", entity.getName());
            return null;
        }

        return selectOne.getMtType();
    }

    @Override
    public boolean startP2P(StartMeetingMtVo startMeetingMtVo) {

        log.info("开启点对点会议请求参数 : {}", startMeetingMtVo);
        MtEntity entity = mtMapper.selectById(startMeetingMtVo.getDbId());
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        String ip = getIp(startMeetingMtVo.getDstId(), startMeetingMtVo.getCallType());

        RemoteMt remotemt = new RemoteMt();
        remotemt.setIp(ip);
        remotemt.setAlias(startMeetingMtVo.getAlias());
        remotemt.setType(startMeetingMtVo.getAddrType());
        StartP2P startP2P = new StartP2P();
        startP2P.setRemotemt(remotemt);
        startP2P.setRate(startMeetingMtVo.getBitrate());

        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/p2p/{ssid}/{ssno}", JSON.toJSONString(startP2P), String.class, paramMap);
        log.info("开启点对点会议响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "开启点对点会议失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_START_P2P_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean stopP2P(Integer dbId) {

        log.info("停止点对点会议请求参数 : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());

        log.info("停止点对点会议请求参数 ssid : {}", entity.getMtid());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate()
                .exchange(mtRequestUrl + "/p2p/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, paramMap);
        log.info("停止点对点会议响应参数 exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "停止点对点会议失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_STOP_P2P_FAILED, mtResponse);

        return true;
    }

    @Override
    public GetMtStatusResponseVo getMtStatus(Integer dbId) {

        log.info("获取终端状态请求参数 : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());

        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate()
                .exchange(mtRequestUrl + "/status/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("获取终端状态响应参数 exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "获取终端状态失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_GET_MT_STATUS_FAILED, mtResponse);
        MtStatusResponse mtStatusResponse = JSONObject.parseObject(exchange.getBody(), MtStatusResponse.class);

        return MtConvert.INSTANCE.convertGetMtStatusResponseVo(mtStatusResponse.getMtStatus());
    }

    @Override
    public boolean setDumbMute(Integer dbId, Boolean mute, String open) {

        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        Integer state = "true".equals(open) ? 1 : 0;
        SetDumbOrMuteVo dumbOrMuteVo = new SetDumbOrMuteVo();
        dumbOrMuteVo.setState(state);

        return mute ? setMute(paramMap, dumbOrMuteVo) : setDumb(paramMap, dumbOrMuteVo);
    }

    public boolean setMute(Map<String, Long> paramMap, SetDumbOrMuteVo dumbOrMuteVo) {

        // 设置静音
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/silence/{ssid}/{ssno}", JSON.toJSONString(dumbOrMuteVo), String.class, paramMap);
        log.info("设置静音响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "设置静音失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_MUTE_FAILED, mtResponse);

        return true;
    }

    public boolean setDumb(Map<String, Long> paramMap, SetDumbOrMuteVo dumbOrMuteVo) {

        // 设置哑音
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/mute/{ssid}/{ssno}", JSON.toJSONString(dumbOrMuteVo), String.class, paramMap);
        log.info("设置哑音响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "设置哑音失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_DUMB_FAILED, mtResponse);

        return true;
    }

    @Override
    public GetDumbMuteVo getDumbMute(Integer dbId, String mute) {

        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        String state = "true".equals(mute) ? getMute(paramMap) : getDumb(paramMap);
        GetDumbMuteVo getDumbMuteVo = new GetDumbMuteVo();
        getDumbMuteVo.setMute(mute);
        getDumbMuteVo.setOpen(state);

        return getDumbMuteVo;
    }

    public String getMute(Map<String, Long> paramMap) {

        // 获取静音
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate()
                .exchange(mtRequestUrl + "/silence/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("获取静音响应参数 exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "获取静音失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_GET_MUTE_FAILED, mtResponse);
        JSONObject object = JSONObject.parseObject(exchange.getBody());

        return String.valueOf(object.get("state"));
    }

    public String getDumb(Map<String, Long> paramMap) {

        // 获取哑音
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate()
                .exchange(mtRequestUrl + "/mute/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("获取哑音响应参数 exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "获取哑音失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_GET_DUMB_FAILED, mtResponse);
        JSONObject object = JSONObject.parseObject(exchange.getBody());

        return String.valueOf(object.get("state"));
    }

    @Override
    public boolean setVolume(Integer dbId, Integer type, Integer volume) {

        log.info("设置音量请求参数, id : {}, type : {}, volume : {}", dbId, type, volume);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        SetVolumeVo setVolumeVo = new SetVolumeVo();
        setVolumeVo.setType(type);
        setVolumeVo.setVolume(volume);

        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/volume/{ssid}/{ssno}", JSON.toJSONString(setVolumeVo), String.class, paramMap);
        log.info("设置音量响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "设置音量失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_VOLUME_FAILED, mtResponse);

        return true;
    }

    @Override
    public String getVolume(Integer dbId, Integer type) {

        log.info("音量获取请求参数, id : {}, type : {}", dbId, type);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        GetVolumeVo getVolumeVo = new GetVolumeVo();
        getVolumeVo.setType(type);

        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/volumes/{ssid}/{ssno}", JSON.toJSONString(getVolumeVo), String.class, paramMap);
        log.info("音量获取响应参数 response : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "音量获取失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_GET_VOLUME_FAILED, mtResponse);
        JSONObject object = JSONObject.parseObject(response);

        return String.valueOf(object.get("volume"));
    }

    @Override
    public boolean keyframe(Integer dbId, Integer streamType) {

        log.info("请求关键帧请求参数, id : {}, streamType : {}", dbId, streamType);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        KeyframeVo keyframeVo = new KeyframeVo();
        keyframeVo.setStreamType(streamType);

        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/volume/{ssid}/{ssno}", JSON.toJSONString(keyframeVo), String.class, paramMap);
        log.info("请求关键帧响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "请求关键帧失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_KEYFRAME_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean mtStartDual(Integer dbId, boolean type, boolean isLocal) {

        log.info("双流控制请求参数, id : {}, start : {}, isLocal: {}", dbId, type, isLocal);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        MtStartDualVo mtStartDualVo = new MtStartDualVo();
        mtStartDualVo.setType(type ? 0 : 1);
        mtStartDualVo.setIsLocal(isLocal ? 1 : 0);

        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/dual/{ssid}/{ssno}", JSON.toJSONString(mtStartDualVo), String.class, paramMap);
        log.info("双流控制响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "双流控制失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_START_DUAL_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean ptzCtrl(Integer dbId, Integer type, Integer ptzCmd) {

        log.info("ptz控制请求参数, id : {}, start : {}, ptzCmd: {}", dbId, type, ptzCmd);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        PtzCtrlVo ptzCtrlVo = new PtzCtrlVo();
        ptzCtrlVo.setType(type);
        ptzCtrlVo.setPtzCmd(ptzCmd);

        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/ptz/{ssid}/{ssno}", JSON.toJSONString(ptzCtrlVo), String.class, paramMap);
        log.info("ptz控制响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "ptz控制失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_PTZ_CONTROLLER_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean setPipMode(Integer dbId, Integer mode) {

        log.info("设置画面显示模式请求参数, id : {}, mode : {}", dbId, mode);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        SetPipModeVo setPipModeVo = new SetPipModeVo();
        setPipModeVo.setMode(mode);

        String response = remoteRestTemplate.getRestTemplate()
                .postForObject(mtRequestUrl + "/pip/{ssid}/{ssno}", JSON.toJSONString(setPipModeVo), String.class, paramMap);
        log.info("设置画面显示模式响应参数 : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "设置画面显示模式失败 : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_PIP_MODE_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean mtNotify(String notify) {

        log.info("终端通知信息 : {}", notify);
        JSONObject jsonObject = JSONObject.parseObject(notify);
        Integer ssid = (Integer) jsonObject.get("ssid");
        Integer type = (Integer) jsonObject.get("type");

        LambdaQueryWrapper<MtEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtEntity::getMtid, ssid);
        MtEntity mtEntity = mtMapper.selectOne(wrapper);
        if (mtEntity == null) {
            return false;
        }
        NotifyHandler.getInstance().distributeMessages(ssid, mtEntity.getDevtype(), type, notify);

        return true;
    }

    private void check(MtEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            throw new MtException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if (entity.getMtid()==null) {
            throw new MtException(DeviceErrorEnum.DEVICE_NOT_LOGIN);
        }
    }

    public Map<String, Long> setParamMap(Integer sessionId) {

        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());
        if (sessionId != null) {
            paramMap.put("ssid", Long.valueOf(sessionId));
        }

        return paramMap;
    }

    public String getIp(Integer dstId, Integer callType) {

        if (callType == 0 ) {
            // 对端为svr，对应监控平台ip
            CuEntity cuEntity = cuMapper.selectById(dstId);
            return cuEntity.getIp();
        }
        MtEntity mtEntity = mtMapper.selectById(dstId);

        return mtEntity.getIp();
    }
}
