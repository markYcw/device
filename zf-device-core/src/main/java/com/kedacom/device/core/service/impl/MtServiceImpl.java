package com.kedacom.device.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.constant.MtConstants;
import com.kedacom.device.core.convert.MtConvert;
import com.kedacom.device.core.entity.MtEntity;
import com.kedacom.device.core.entity.MtTypeEntity;
import com.kedacom.device.core.exception.MtException;
import com.kedacom.device.core.mapper.MtMapper;
import com.kedacom.device.core.mapper.MtTypeMapper;
import com.kedacom.device.core.mapper.SvrMapper;
import com.kedacom.device.core.ping.DefaultPing;
import com.kedacom.device.core.ping.PingInfo;
import com.kedacom.device.core.service.MtService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.MtUrlFactory;
import com.kedacom.device.core.utils.RestTemplateConfigure;
import com.kedacom.mt.*;
import com.kedacom.mt.response.GetMtStatusResponseVo;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
    SvrMapper svrMapper;

    @Resource
    DefaultPing defaultPing;

    @Resource
    MtTypeMapper mtTypeMapper;

    @Resource
    MtUrlFactory mtUrlFactory;

    @Resource
    HandleResponseUtil responseUtil;

    @Resource
    RestTemplateConfigure restTemplateConfigure;

    @Value("${mt.maintain.heartbeat:true}")
    private boolean heartbeat;

    public static AtomicBoolean MT_CHECK_SWITCH = new AtomicBoolean(true);

    public static AtomicBoolean MT_MAINTAIN_HEARTBEAT_PERIOD = new AtomicBoolean(false);

    private final static String NTY_URL = "http://127.0.0.1:9000/api/api-device/ums/device/notify";

    /**
     * ?????????????????????id???
     */
    public static Set<Integer> synHashSet = Collections.synchronizedSet(new HashSet<Integer>());

    /**
     * ???????????????????????????????????????????????????????????????
     */
    public static Set<Integer> synInvalidHashSet = Collections.synchronizedSet(new HashSet<Integer>());

    /**
     * ???????????????????????????id??????????????????????????????????????????????????????id?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    public static Set<Integer> synTransitHashSet = Collections.synchronizedSet(new HashSet<Integer>());

    @Override
    public Page<TerminalVo> mtPage(TerminalQuery terminalQuery) {

        log.info("??????????????????????????????TerminalQuery : {}", terminalQuery);
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
            log.error("??????????????????????????????");
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

        if (!heartbeat) {

            return terminalVoList;
        }
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        Set<Integer> synTemporaryHashSet = Collections.synchronizedSet(new HashSet<Integer>());
        synTemporaryHashSet.addAll(synHashSet);
        synTemporaryHashSet.addAll(synTransitHashSet);
        synTemporaryHashSet.removeAll(synInvalidHashSet);

        if (CollectionUtil.isEmpty(synTemporaryHashSet)) {
            return terminalVoList;
        }
        out : for (Integer dbId : synTemporaryHashSet) {
            for (TerminalVo terminalVo : terminalVoList) {
                if (dbId.equals(terminalVo.getId())) {
                    terminalVo.setStatus(1);
                    continue out;
                }
            }
        }
        synTemporaryHashSet.clear();

        return terminalVoList;
    }

    @Override
    public TerminalVo queryMt(Integer dbId) {

        log.info("?????????????????????????????? : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        List<MtEntity> entities = new ArrayList<>();
        entities.add(entity);
        List<TerminalVo> terminalVoList = queryConnectionStatus(entities);

        return terminalVoList.get(0);
    }

    @Override
    public List<Integer> queryIdsByMtId() {

        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(MtEntity::getMtid);
        List<MtEntity> mtEntities = mtMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(mtEntities)) {
            log.error("??????mtId??????????????????????????????");
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

        log.info("?????????????????????????????? : {}", terminalVo);
        MtEntity entity = MtConvert.INSTANCE.convertMtEntity(terminalVo);
        mtMapper.insert(entity);
        terminalVo.setId(entity.getId());

        return terminalVo;
    }

    @Override
    public boolean updateMt(TerminalVo terminalVo) {

        log.info("?????????????????????????????? : {}", terminalVo);
        MtEntity entity = MtConvert.INSTANCE.convertMtEntity(terminalVo);

        return mtMapper.updateById(entity) > 0;
    }

    @Override
    public boolean deleteMt(List<Integer> dbIds) {

        log.info("?????????????????????????????? : {}", dbIds);
        int batchIds = mtMapper.deleteBatchIds(dbIds);

        return batchIds == dbIds.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Integer loginById(Integer dbId) {

        log.info("???????????????????????? : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        if (entity == null) {
            throw new MtException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        LoginParamVo loginParamVo = MtConvert.INSTANCE.convertLoginParamVo(entity);
        loginParamVo.setNtyUrl(NTY_URL);
        Map<String, Long> paramMap = setParamMap(null);

        log.info("?????????????????????????????? loginParamVo : {}", loginParamVo);
        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/login/{ssno}", JSON.toJSONString(loginParamVo), String.class, paramMap);
        log.info("?????????????????????????????? response : {}", response);
        MtLoginResponse mtLoginResponse = JSON.parseObject(response, MtLoginResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtLoginResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_LOGIN_FAILED, mtLoginResponse);
        entity.setMtid(mtLoginResponse.getSsid());
        mtMapper.updateById(entity);

        if (heartbeat) {
            if (MT_MAINTAIN_HEARTBEAT_PERIOD.get()) {
                synTransitHashSet.add(dbId);
                log.info("?????? : {}, ???????????????????????????????????? synTransitHashSet : {}", dbId, synTransitHashSet);
            } else {
                synHashSet.add(dbId);
                log.info("?????? : {}, ?????????????????????????????? synHashSet : {}", dbId, synHashSet);
            }
        }

        return mtLoginResponse.getSsid();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean logOutById(Integer dbId) {

        log.info("???????????????????????????????????? : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);

        Integer mtId = entity.getMtid();
        Map<String, Long> paramMap = setParamMap(mtId);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();

        LambdaUpdateWrapper<MtEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MtEntity::getId, dbId)
                .set(MtEntity::getMtid, null);
        mtMapper.update(null, updateWrapper);

        if (heartbeat && !MT_MAINTAIN_HEARTBEAT_PERIOD.get()) {
            // ???????????????????????????????????????????????????????????????id???????????????????????????????????????
            synHashSet.remove(dbId);
        }
        log.info("???????????????????????????????????? ssid : {}", mtId);
        ResponseEntity<String> exchange = restTemplateConfigure.getRestTemplate()
                .exchange(mtRequestUrl + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, paramMap);
        log.info("???????????????????????????????????? exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "???????????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_LOGOUT_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean heartBeat(Integer dbId) {

        log.info("???????????????????????? : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        // ????????????????????????????????????????????????????????????????????????????????????
        if (MT_CHECK_SWITCH.get()) {
            check(entity);
        }
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();

        ResponseEntity<String> exchange = restTemplateConfigure.getRestTemplate()
                .exchange(mtRequestUrl + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("???????????????????????? exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_HEART_BEAT_FAILED, mtResponse);

        return true;
    }

    @Override
    public Integer getMtType(Integer dbId) {

        log.info("?????????????????????????????? : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);

        LambdaQueryWrapper<MtTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MtTypeEntity::getMtName, entity.getMtname());
        MtTypeEntity selectOne = mtTypeMapper.selectOne(queryWrapper);
        if (selectOne == null) {
            log.error("????????? : {} ????????????????????????", entity.getName());
            return null;
        }

        return selectOne.getMtType();
    }

    @Override
    public boolean startP2P(StartMeetingMtVo startMeetingMtVo) {

        log.info("????????????????????????????????? : {}", startMeetingMtVo);
        MtEntity entity = mtMapper.selectById(startMeetingMtVo.getDbId());
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());

        StartP2P startP2P = structureParam(startMeetingMtVo);
        if (startP2P == null) {
            throw new MtException(null, "??????????????????????????????????????????????????????");
        }
        log.info("?????????????????????-????????????????????? : {}", startP2P.toString());
        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/p2p/{ssid}/{ssno}", JSON.toJSONString(startP2P), String.class, paramMap);
        log.info("????????????????????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "??????????????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_START_P2P_FAILED, mtResponse);

        return true;
    }

    public StartP2P structureParam(StartMeetingMtVo startMeetingMtVo) {

        // ???????????????????????????????????? ip??? e164??? h323??? id
        Integer key = startMeetingMtVo.getKey();
        // ????????????????????????????????? ip??? e164??? h323
        Integer addrType = startMeetingMtVo.getAddrType();
        String ip = null, e164 = null, h323 = null;
        String value = startMeetingMtVo.getValue();
        if (key.equals(MtConstants.IP)) {
            ip = value;
        } else if (key.equals(MtConstants.E164)) {
            e164 = value;
        } else if (key.equals(MtConstants.H323)) {
            h323 = value;
        } else {
            MtEntity entity = mtMapper.selectById(value);
            Integer callType = startMeetingMtVo.getCallType();
            if (addrType.equals(MtConstants.IP)) {
                // ????????? svr ???????????? ip ?????????e164 ??? h323 ????????????????????????
                ip = callType == 1 ? entity.getIp() : getIp(value);
            } else if (addrType.equals(MtConstants.E164)) {
                e164 = entity.getE164();
            } else {
                h323 = entity.getUpuname();
                if (StrUtil.isBlank(h323)) {
                    return null;
                }
            }
        }

        RemoteMt remotemt = new RemoteMt();
        if (addrType.equals(MtConstants.IP)) {
            remotemt.setIp(ip);
            remotemt.setAlias("");
        } else if (addrType.equals(MtConstants.E164)) {
            remotemt.setIp("");
            remotemt.setAlias(e164);
        } else {
            remotemt.setIp("");
            remotemt.setAlias(h323);
        }
        remotemt.setType(addrType);
        Integer callType = startMeetingMtVo.getCallType();
        StartP2P startP2P = new StartP2P();
        startP2P.setRemoteMt(remotemt);
        startP2P.setDevType(callType == 0 ? 1 : 0);
        startP2P.setRate(startMeetingMtVo.getBitrate());

        return startP2P;
    }

    @Override
    public boolean stopP2P(Integer dbId) {

        log.info("????????????????????????????????? : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());

        log.info("????????????????????????????????? ssid : {}", entity.getMtid());
        ResponseEntity<String> exchange = restTemplateConfigure.getRestTemplate()
                .exchange(mtRequestUrl + "/p2p/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, paramMap);
        log.info("????????????????????????????????? exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "??????????????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_STOP_P2P_FAILED, mtResponse);

        return true;
    }

    @Override
    public GetMtStatusResponseVo getMtStatus(Integer dbId) {

        log.info("?????????????????????????????? : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());

        ResponseEntity<String> exchange = restTemplateConfigure.getRestTemplate()
                .exchange(mtRequestUrl + "/status/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("?????????????????????????????? exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "???????????????????????? : {}, {}, {}";
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
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();

        return mute ? setMute(paramMap, mtRequestUrl, dumbOrMuteVo) : setDumb(paramMap, mtRequestUrl, dumbOrMuteVo);
    }

    public boolean setMute(Map<String, Long> paramMap, String mtRequestUrl, SetDumbOrMuteVo dumbOrMuteVo) {

        // ????????????
        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/silence/{ssid}/{ssno}", JSON.toJSONString(dumbOrMuteVo), String.class, paramMap);
        log.info("???????????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_MUTE_FAILED, mtResponse);

        return true;
    }

    public boolean setDumb(Map<String, Long> paramMap, String mtRequestUrl, SetDumbOrMuteVo dumbOrMuteVo) {

        // ????????????
        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/mute/{ssid}/{ssno}", JSON.toJSONString(dumbOrMuteVo), String.class, paramMap);
        log.info("???????????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_DUMB_FAILED, mtResponse);

        return true;
    }

    @Override
    public GetDumbMuteVo getDumbMute(Integer dbId, String mute) {

        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        String state = "true".equals(mute) ? getMute(paramMap, mtRequestUrl) : getDumb(paramMap, mtRequestUrl);
        GetDumbMuteVo getDumbMuteVo = new GetDumbMuteVo();
        getDumbMuteVo.setMute(mute);
        getDumbMuteVo.setOpen(state);

        return getDumbMuteVo;
    }

    public String getMute(Map<String, Long> paramMap, String mtRequestUrl) {

        // ????????????
        ResponseEntity<String> exchange = restTemplateConfigure.getRestTemplate()
                .exchange(mtRequestUrl + "/silence/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("???????????????????????? exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_GET_MUTE_FAILED, mtResponse);
        JSONObject object = JSONObject.parseObject(exchange.getBody());

        return String.valueOf(object.get("state"));
    }

    public String getDumb(Map<String, Long> paramMap, String mtRequestUrl) {

        // ????????????
        ResponseEntity<String> exchange = restTemplateConfigure.getRestTemplate()
                .exchange(mtRequestUrl + "/mute/{ssid}/{ssno}", HttpMethod.GET, null, String.class, paramMap);
        log.info("???????????????????????? exchange : {}", exchange);
        MtResponse mtResponse = JSONObject.parseObject(exchange.getBody(), MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_GET_DUMB_FAILED, mtResponse);
        JSONObject object = JSONObject.parseObject(exchange.getBody());

        return String.valueOf(object.get("state"));
    }

    @Override
    public boolean setVolume(Integer dbId, Integer type, Integer volume) {

        log.info("????????????????????????, id : {}, type : {}, volume : {}", dbId, type, volume);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        SetVolumeVo setVolumeVo = new SetVolumeVo();
        setVolumeVo.setType(type);
        setVolumeVo.setVolume(volume);

        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/volume/{ssid}/{ssno}", JSON.toJSONString(setVolumeVo), String.class, paramMap);
        log.info("???????????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_VOLUME_FAILED, mtResponse);

        return true;
    }

    @Override
    public String getVolume(Integer dbId, Integer type) {

        log.info("????????????????????????, id : {}, type : {}", dbId, type);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        GetVolumeVo getVolumeVo = new GetVolumeVo();
        getVolumeVo.setType(type);

        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/volumes/{ssid}/{ssno}", JSON.toJSONString(getVolumeVo), String.class, paramMap);
        log.info("???????????????????????? response : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_GET_VOLUME_FAILED, mtResponse);
        JSONObject object = JSONObject.parseObject(response);

        return String.valueOf(object.get("volume"));
    }

    @Override
    public boolean keyframe(Integer dbId, Integer streamType) {

        log.info("???????????????????????????, id : {}, streamType : {}", dbId, streamType);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        KeyframeVo keyframeVo = new KeyframeVo();
        keyframeVo.setStreamType(streamType);

        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/keyframe/{ssid}/{ssno}", JSON.toJSONString(keyframeVo), String.class, paramMap);
        log.info("??????????????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "????????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_KEYFRAME_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean mtStartDual(Integer dbId, boolean type, boolean isLocal) {

        log.info("????????????????????????, id : {}, start : {}, isLocal: {}", dbId, type, isLocal);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        MtStartDualVo mtStartDualVo = new MtStartDualVo();
        mtStartDualVo.setType(type ? 1 : 0);
        mtStartDualVo.setIsLocal(isLocal ? 1 : 0);

        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/dual/{ssid}/{ssno}", JSON.toJSONString(mtStartDualVo), String.class, paramMap);
        log.info("???????????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "?????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_START_DUAL_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean ptzCtrl(Integer dbId, Integer type, Integer ptzCmd) {

        log.info("ptz??????????????????, id : {}, start : {}, ptzCmd: {}", dbId, type, ptzCmd);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        PtzCtrlVo ptzCtrlVo = new PtzCtrlVo();
        ptzCtrlVo.setType(type);
        ptzCtrlVo.setPtzCmd(ptzCmd);

        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/ptz/{ssid}/{ssno}", JSON.toJSONString(ptzCtrlVo), String.class, paramMap);
        log.info("ptz?????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "ptz???????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_PTZ_CONTROLLER_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean setPipMode(Integer dbId, Integer mode) {

        log.info("????????????????????????????????????, id : {}, mode : {}", dbId, mode);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        String mtRequestUrl = mtUrlFactory.getMtRequestUrl();
        Map<String, Long> paramMap = setParamMap(entity.getMtid());
        SetPipModeVo setPipModeVo = new SetPipModeVo();
        setPipModeVo.setMode(mode);

        String response = restTemplateConfigure.getRestTemplate()
                .postForObject(mtRequestUrl + "/pip/{ssid}/{ssno}", JSON.toJSONString(setPipModeVo), String.class, paramMap);
        log.info("???????????????????????????????????? : {}", response);
        MtResponse mtResponse = JSONObject.parseObject(response, MtResponse.class);
        String errorMsg = "?????????????????????????????? : {}, {}, {}";
        assert mtResponse != null;
        responseUtil.handleMtRes(errorMsg, DeviceErrorEnum.MT_SET_PIP_MODE_FAILED, mtResponse);

        return true;
    }

    @Override
    public boolean ping(Integer dbId) {

        log.info("ping ??????????????????, id : {}", dbId);
        MtEntity entity = mtMapper.selectById(dbId);
        check(entity);
        PingInfo pingInfo = new PingInfo();
        pingInfo.setIp(entity.getIp());

        return defaultPing.isAlive(pingInfo);
    }

    @Override
    public boolean check(Integer callType, String value) {

        boolean flag;
        if (callType == 1) {
            MtEntity mtEntity = mtMapper.selectById(value);
            flag = mtEntity == null;
        } else {
            SvrEntity svrEntity = svrMapper.selectById(value);
            flag = svrEntity == null;
        }

        return flag;
    }

    private void check(MtEntity entity) {

        if (ObjectUtils.isEmpty(entity)) {
            throw new MtException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        // ????????????????????????????????????????????????
        if (!heartbeat || !synHashSet.contains(entity.getId())) {
            log.info("?????? - {} ?????????????????????????????????", entity.getName());
            loginById(entity.getId());
        }
    }

    public Map<String, Long> setParamMap(Integer sessionId) {

        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());
        if (sessionId != null) {
            paramMap.put("ssid", Long.valueOf(sessionId));
        }
        log.info("paramMap : {}", paramMap.toString());

        return paramMap;
    }

    public String getIp(String dstId) {

        // ?????????svr?????????????????????ip
        SvrEntity svrEntity = svrMapper.selectById(dstId);

        return svrEntity.getIp();
    }

}
