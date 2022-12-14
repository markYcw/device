package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.SvrConvert;
import com.kedacom.device.core.basicParam.SvrBasicParam;
import com.kedacom.device.core.enums.DeviceModelType;
import com.kedacom.device.core.exception.CuException;
import com.kedacom.device.core.exception.SvrException;
import com.kedacom.device.core.mapper.SvrMapper;
import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyHandler;
import com.kedacom.device.core.ping.DefaultPing;
import com.kedacom.device.core.ping.PingInfo;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.device.core.utils.*;
import com.kedacom.device.svr.SvrResponse;
import com.kedacom.device.svr.pojo.MergeInfo;
import com.kedacom.device.svr.pojo.PicChn;
import com.kedacom.device.svr.request.SvrLoginRequest;
import com.kedacom.device.svr.response.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.PicChnVo;
import com.kedacom.svr.pojo.RemotePoint;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.vo.*;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/6 10:50
 * @description
 */
@Slf4j
@Service("svrService")
public class SvrServiceImpl extends ServiceImpl<SvrMapper, SvrEntity> implements SvrService {

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Autowired
    private SvrMapper svrMapper;

    @Autowired
    private SvrConvert convert;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Resource
    DefaultPing defaultPing;

    @Value("${zf.svrNtyUrl.server_addr:127.0.0.1:9000}")
    private String svrNtyUrl;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/device/notify";

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String SVR_REQUEST_HEAD = "http://";

    //svr2.0??????????????????
    private final static String SVR = "/mid/v2/svr";

    @Override
    public BaseResult<BasePage<SvrEntity>> pageQuery(SvrPageQueryDTO queryDTO) {
        Page<SvrEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<SvrEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(queryDTO.getIp())) {
            queryWrapper.like(SvrEntity::getIp, queryDTO.getIp());
        }
        if (!StringUtils.isEmpty(queryDTO.getName())) {
            queryWrapper.like(SvrEntity::getName, queryDTO.getName());
        }

        Page<SvrEntity> platformEntityPage = svrMapper.selectPage(page, queryWrapper);
        List<SvrEntity> records = platformEntityPage.getRecords();

        BasePage<SvrEntity> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(records);
        return BaseResult.succeed(basePage);
    }

    @Override
    public void svrNotify(String notify) {
        log.info("svr??????:{}", notify);
        JSONObject jsonObject = JSONObject.parseObject(notify);
        Integer type = (Integer) jsonObject.get("type");
        Integer ssid = (Integer) jsonObject.get("ssid");

        if (ssid != null) {
            NotifyHandler.getInstance().distributeMessages(ssid, DeviceType.SVR.getValue(), type, notify);
        }

    }

    @Override
    public BaseResult<SvrLoginVO> loginById(Integer id) {
        log.info("??????svr????????????:{}", id);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        SvrEntity entity = svrMapper.selectById(id);
        if (entity == null) {
            throw new SvrException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        SvrLoginRequest request = convert.convertToSvrLoginRequest(entity);
        String ntyUrl = REQUEST_HEAD + svrNtyUrl + NOTIFY_URL;
        request.setNtyUrl(ntyUrl);
        String url = getUrl();
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());

        log.info("??????SVR?????????????????????:{}", JSON.toJSONString(request));
        String string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        log.info("??????SVR???????????????:{}", string);

        SvrLoginResponse response = JSON.parseObject(string, SvrLoginResponse.class);
        String errorMsg = "SVR????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_LOGIN_FAILED, response);
        entity.setSsid(response.getSsid());
        entity.setModifyTime(new Date());
        svrMapper.updateById(entity);

        SvrLoginVO loginVO = new SvrLoginVO();
        loginVO.setSsid(response.getSsid());
        return BaseResult.succeed(loginVO);
    }

    public Integer logById(Integer id) {
        log.info("??????svr????????????:{}", id);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        SvrEntity entity = svrMapper.selectById(id);
        if (entity == null) {
            throw new SvrException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        SvrLoginRequest request = convert.convertToSvrLoginRequest(entity);
        String ntyUrl = REQUEST_HEAD + svrNtyUrl + NOTIFY_URL;
        request.setNtyUrl(ntyUrl);
        String url = getUrl();
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());

        log.info("??????SVR?????????????????????:{}", JSON.toJSONString(request));
        String string = null;
        try {
            string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        } catch (RestClientException e) {
            log.info("=======??????svr??????{}", e);
            return 0;
        }
        log.info("??????SVR???????????????:{}", string);

        SvrLoginResponse response = JSON.parseObject(string, SvrLoginResponse.class);
        if (response.getCode()!=0){
            log.info("=======??????svr??????code!=0");
            return 0;
        }
        entity.setSsid(response.getSsid());
        svrMapper.updateById(entity);
        return response.getSsid();
    }

    @Override
    public BaseResult logoutById(Integer id) {
        log.info("??????ID??????svr????????????{}", id);
        SvrEntity entity = svrMapper.selectById(id);
        check(entity);
        SvrBasicParam param = getParam(logById(id));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("??????svr??????{}", response);
        String errorMsg = "??????SVR??????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_LOGOUT_FAILED, response);
        LambdaUpdateWrapper<SvrEntity> wrapper = new LambdaUpdateWrapper();
        wrapper.set(SvrEntity::getSsid, null)
                .set(SvrEntity::getModifyTime, new Date())
                .eq(SvrEntity::getId, id);
        svrMapper.update(null, wrapper);
        return BaseResult.succeed("??????SVR??????");
    }

    @Override
    public BaseResult<SvrCapVo> svrCap(Integer dbId) {
        log.info("??????SVR?????????????????????{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/svrcap/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrCapResponse response = JSONObject.parseObject(exchange.getBody(), SvrCapResponse.class);
        log.info("??????SVR???????????????{}", exchange.getBody());
        String errorMsg = "??????SVR???????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_CAP_FAILED, response);
        SvrCapVo svrCapVo = convert.convertTOSvrCapVo(response);
        return BaseResult.succeed("??????svr???????????????", svrCapVo);
    }

    @Override
    public void synData() {
        log.info("=========??????????????????svr??????????????????");
        LambdaQueryWrapper<SvrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SvrEntity::getModelType,"SVR28??????");
        List<SvrEntity> list = svrMapper.selectList(wrapper);
        log.info("????????????svr28???????????????:{}",list);
        if (CollectionUtil.isNotEmpty(list)) {
            Iterator<SvrEntity> iterator = list.iterator();
            try {
                while (iterator.hasNext()) {
                    SvrEntity next = iterator.next();
                    log.info("====????????????svr28??????IP???{}",next.getIp());
                    next.setPort(8765);
                    next.setWebPort(8766);
                    svrMapper.updateById(next);
                    boolean alive = defaultPing.isAlive(new PingInfo(next.getIp()));
                    log.info("=======??????ping???????????????{},??????IP???{}",alive,next.getIp());
                    if(!alive){
                        continue;
                    }
                    Integer ssid = this.logById(next.getId());
                    if(ssid>0){
                        BaseResult<SvrCapVo> result = this.svrCap(next.getId());
                        String modelType = result.getData().getSvrModel().substring(0, 7);
                        next.setModelType(modelType);
                        svrMapper.updateById(next);
                    }else {
                        log.info("======??????8765????????????????????????9765ip{}",next.getIp());
                        next.setPort(9765);
                        next.setWebPort(9766);
                        svrMapper.updateById(next);
                        Integer rid =  this.logById(next.getId());
                        if(rid>0){
                            BaseResult<SvrCapVo> result = this.svrCap(next.getId());
                            String modelType = result.getData().getSvrModel().substring(0, 7);
                            next.setModelType(modelType);
                            svrMapper.updateById(next);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("?????????????????????svr??????{}",e);
            }
        }
    }

    @Override
    public BaseResult<String> svrTime(Integer dbId) {
        log.info("??????SVR??????????????????{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/svrtime/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrTimeResponse response = JSONObject.parseObject(exchange.getBody(), SvrTimeResponse.class);
        log.info("??????SVR??????????????????{}", exchange.getBody());
        String errorMsg = "??????SVR????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_TIME_FAILED, response);
        return BaseResult.succeed("??????SVR????????????", response.getTime());
    }

    @Override
    public BaseResult<String> searchDev(Integer dbId) {
        log.info("?????????????????????????????????{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/searchdev/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("?????????????????????????????????{}", exchange.getBody());
        String errorMsg = "???????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_SEARCH_DEV_FAILED, response);
        return BaseResult.succeed("???????????????????????????");
    }

    @Override
    public BaseResult<List<EnChnListVo>> enChnList(Integer dbId) {
        log.info("????????????????????????????????????{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/encchnlist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("===============????????????????????????:{}", exchange.getBody());
        ChnListExtendsResponse response = JSONObject.parseObject(exchange.getBody(), ChnListExtendsResponse.class);
        log.info("????????????????????????????????????{}", exchange.getBody());
        String errorMsg = "??????????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_CHN_LIST_FAILED, response);
        List<EnChnListVo> listVos = response.getChnList().stream().map(enChnList -> convert.convertToEnChnListVo(enChnList)).collect(Collectors.toList());
        return BaseResult.succeed("??????????????????????????????", listVos);
    }

    @Override
    public BaseResult<String> enChn(EnChnDto enChnDto) {
        log.info("??????/??????????????????????????????EnChnDto:{}", enChnDto);
        SvrEntity entity = svrMapper.selectById(enChnDto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(enChnDto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/encchn/{ssid}/{ssno}", JSON.toJSONString(enChnDto), String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(s, SvrResponse.class);
        log.info("??????/??????????????????????????????{}", response);
        String errorMsg = "??????/????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_CHN_FAILED, response);
        return BaseResult.succeed("????????????");
    }

    @Override
    public BaseResult<CpResetVo> enCpReset(GetIpcItemRequestVo dto) {
        log.info("???????????????????????????????????????EnCpResetDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/encpreset/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        CpResetResponse response = JSON.parseObject(s, CpResetResponse.class);
        log.info("???????????????????????????????????????{}", response);
        String errorMsg = "?????????????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_CP_RESET_FAILED, response);
        CpResetVo cpResetVo = convert.convertToCpResetVo(response);
        return BaseResult.succeed("?????????????????????????????????", cpResetVo);
    }

    @Override
    public BaseResult<String> CpReset(CpResetDto dto) {
        log.info("????????????????????????????????????CpResetDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/encpreset/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("????????????????????????????????????{}", exchange.getBody());
        String errorMsg = "??????????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_CP_RESET_FAILED, response);
        return BaseResult.succeed("??????????????????????????????");
    }

    @Override
    public BaseResult<DeChnListVo> deChnList(Integer dbId) {
        log.info("????????????????????????????????????:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/decchnlist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("????????????????????????????????????{}", exchange.getBody());
        DecChnListResponse response = JSON.parseObject(exchange.getBody(), DecChnListResponse.class);
        log.info("????????????????????????????????????{}", exchange.getBody());
        String errorMsg = "??????????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DE_CHN_LIST_FAILED, response);
        DeChnListVo deChnListVo = convert.convertToDeChnListVo(response);
        return BaseResult.succeed("??????????????????????????????", deChnListVo);
    }

    @Override
    public BaseResult<String> deChn(DeChnDto dto) {
        log.info("??????/??????????????????????????????DeChnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/decchn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("??????/??????????????????????????????{}", response);
        String errorMsg = "??????/????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DE_CHN_FAILED, response);
        return BaseResult.succeed("????????????");
    }

    @Override
    public BaseResult<DecParamVo> decParam(DecParamDto dto) {
        log.info("??????????????????????????????DecParamDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/decparam/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        DecParamResponse response = JSON.parseObject(s, DecParamResponse.class);
        log.info("??????????????????????????????{}", response);
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DEC_PARAM_FAILED, response);
        DecParamVo decParamVo = convert.convertToDecParamVo(response);
        return BaseResult.succeed("????????????????????????", decParamVo);
    }

    @Override
    public BaseResult<String> enDeParam(EnDecParamDto dto) {
        log.info("??????????????????????????????EnDecParamDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/decparam/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("??????????????????????????????{}", exchange.getBody());
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_DEC_PARAM_FAILED, response);
        return BaseResult.succeed("????????????????????????");
    }

    @Override
    public BaseResult<String> ptz(PtzCtrlRequestVo dto) {
        log.info("ptz??????????????????PtzDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/ptz/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("ptz??????????????????{}", exchange.getBody());
        String errorMsg = "ptz????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_PTZ_FAILED, response);
        return BaseResult.succeed("ptz????????????");
    }

    @Override
    public BaseResult<String> remotePoint(RemotePointOnVo dto) {
        log.info("???????????????????????????RemotePointOnVo:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        RemotePointDto pointDto = new RemotePointDto();
        pointDto.setType(0);
        RemotePoint remotePoint = new RemotePoint();
        remotePoint.setName(dto.getRpName());
        remotePoint.setUrl(dto.getUrl());
        pointDto.setRemotePoint(remotePoint);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/remotepoint/{ssid}/{ssno}", JSON.toJSONString(pointDto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("???????????????????????????{}", response);
        String errorMsg = "?????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_FAILED, response);
        return BaseResult.succeed("?????????????????????");
    }

    @Override
    public BaseResult<RemoteCfgVo> remoteCfg(Integer dbId) {
        log.info("?????????????????????????????????dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotecfg/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        RemoteCfgVoResponse response = JSON.parseObject(exchange.getBody(), RemoteCfgVoResponse.class);
        String errorMsg = "???????????????????????????:{},{},{}";
        log.info("?????????????????????????????????{}", exchange.getBody());
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_CFG_FAILED, response);
        RemoteCfgVo remoteCfgVo = convert.convertToRemoteCfgVo(response.getRemoteCfg());
        return BaseResult.succeed("???????????????????????????", remoteCfgVo);
    }

    @Override
    public BaseResult<String> remotePutCfg(RemotePutCfgDto dto) {
        log.info("?????????????????????????????????RemotePutCfgDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String obj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(obj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotecfg/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("?????????????????????????????????{}", exchange.getBody());
        String errorMsg = "???????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_PUT_CFG_FAILED, response);
        return BaseResult.succeed("???????????????????????????");
    }

    @Override
    public BaseResult<String> dual(StartDualRequestVo dto) {
        log.info("????????????????????????DualDto:{}", dto);
        Integer dbId = dto.getDbId();
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/dual/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("????????????????????????{}", exchange.getBody());
        String errorMsg = "??????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DUAL_FAILED, response);
        return BaseResult.succeed("??????????????????");
    }

    @Override
    public BaseResult<String> burn(BurnDto dto) {
        log.info("????????????????????????BurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/burn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("????????????????????????{}", response);
        String errorMsg = "??????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_BURN_FAILED, response);
        return BaseResult.succeed("??????????????????");
    }

    @Override
    public BaseResult<String> reBurn(SupplementBurnVo dto) {
        log.info("??????????????????ReBurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/reburn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("??????????????????{}", response);
        String errorMsg = "????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_RE_BURN_FAILED, response);
        return BaseResult.succeed("????????????");
    }

    @Override
    public BaseResult<String> appendBurn(AppendBurnDto dto) {
        log.info("??????????????????????????????AppendBurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/appendburn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("????????????????????????{}", response);
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_APPEND_BURN_FAILED, response);
        return BaseResult.succeed("????????????????????????");
    }

    @Override
    public BaseResult<String> createBurn(CreateBurnRequestVo dto) {
        log.info("??????????????????????????????CreateBurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        CreateBurnRequestDto create = new CreateBurnRequestDto();
        create.setStartTime(DateUtils.getDateString(dto.getStartTime()));
        create.setEndTime(DateUtils.getDateString(dto.getEndTime()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/createburn/{ssid}/{ssno}", JSON.toJSONString(create), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("????????????????????????{}", response);
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_CREATE_BURN_FAILED, response);
        return BaseResult.succeed("????????????????????????");
    }

    @Override
    public BaseResult<GetBurnTaskResponseVo> burnTaskList(GetBurnTaskRequestVo dto) {
        log.info("??????????????????????????????BurnTaskListDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        GetBurnTaskRequestDto getDto = new GetBurnTaskRequestDto();
        BeanUtils.copyProperties(dto, getDto);
        String start = DateUtils.getDateString(dto.getStartTime());
        getDto.setStartTime(start);
        String end = DateUtils.getDateString(dto.getEndTime());
        getDto.setEndTime(end);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/burntasklist/{ssid}/{ssno}", JSON.toJSONString(getDto), String.class, param.getParamMap());
        BurnTaskResponse response = JSON.parseObject(s, BurnTaskResponse.class);
        String errorMsg = "????????????????????????:{},{},{}";
        log.info("????????????????????????{}", response);
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_BURN_TASK_LIST_FAILED, response);
        GetBurnTaskResponseVo vo = convert.convertTOBurnTaskVo(response);
        return BaseResult.succeed("????????????????????????", vo);
    }

    @Override
    public BaseResult<String> dvdDoor(DvdDoorCtrlVo dto) {
        log.info("DVD????????????????????????DvdDoorDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/dvddoor/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("DVD????????????????????????{}", response);
        String errorMsg = "DVD??????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DVD_DOOR_FAILED, response);
        return BaseResult.succeed("DVD??????????????????");
    }

    @Override
    public BaseResult<RecListResponse> recList(QueryRecVo dto) {
        log.info("????????????????????????RecListDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        QueryRecDto qr = new QueryRecDto();
        qr.setChnid(dto.getChnid());
        qr.setQueryIndex(dto.getQueryIndex());
        qr.setQueryCount(dto.getQueryCount());
        qr.setStarttime(DateUtils.getDateString(dto.getStarttime()));
        qr.setEndtime(DateUtils.getDateString(dto.getEndtime()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/reclist/{ssid}/{ssno}", JSON.toJSONString(qr), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("????????????????????????{}", response);
        String errorMsg = "??????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REC_LIST_FAILED, response);
        RecListResponse recListResponse = JSON.parseObject(s, RecListResponse.class);
        return BaseResult.succeed("??????????????????", recListResponse);
    }

    @Override
    public BaseResult<GetSvrComposePicResponseVo> getMerge(Integer dbId) {
        log.info("??????????????????????????????dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/merge/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        GetMergeResponse response = JSON.parseObject(exchange.getBody(), GetMergeResponse.class);
        log.info("??????????????????????????????{}", exchange.getBody());
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_GET_MERGE_FAILED, response);
        MergeInfo mergeInfo = response.getMergeInfo();
        GetSvrComposePicResponseVo vo = new GetSvrComposePicResponseVo();
        vo.setVideoresolution(mergeInfo.getResolution());
        vo.setBorderwidth(mergeInfo.getBorderWidth());
        vo.setMergestyle(mergeInfo.getMergeStyle());
        vo.setStretchStyle(mergeInfo.getStretchStyle());
        vo.setBorderColorRed(mergeInfo.getBorderColorRed());
        vo.setBorderColorGreen(mergeInfo.getBorderColorGreen());
        vo.setBorderColorBlue(mergeInfo.getBorderColorBlue());
        List<PicChn> list = mergeInfo.getPicChns();
        List<PicChnVo> collect = list.stream().map(a -> convert.convertToPicChnVo(a)).collect(Collectors.toList());
        vo.setPicChns(collect);
        return BaseResult.succeed("????????????????????????", vo);
    }

    @Override
    public BaseResult<String> merge(SetSvrComposePicVo dto) {
        log.info("??????????????????????????????MergeInfoDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/merge/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("??????????????????????????????{}", exchange.getBody());
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_MERGE_FAILED, response);
        return BaseResult.succeed("????????????????????????");
    }

    @Override
    public BaseResult<GetOsdVo> getOsd(Integer dbId) {
        log.info("??????????????????????????????dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/osd/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("????????????????????????{}", exchange.getBody());
        GetOsdResponse response = JSON.parseObject(exchange.getBody(), GetOsdResponse.class);
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_GET_OSD_FAILED, response);
        GetOsdVo vo = convert.convertToGetOsdVo(response);
        log.info("======??????????????????????????????:{}",response);
        log.error("======????????????????????????Vo:{}",vo);
        return BaseResult.succeed("????????????????????????", vo);
    }

    @Override
    public BaseResult<String> osd(OsdSetVo dto) {
        log.info("??????????????????????????????OsdDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/osd/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("????????????????????????{}", exchange.getBody());
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_OSD_FAILED, response);
        return BaseResult.succeed("????????????????????????");
    }

    @Override
    public BaseResult<String> audioAct(SetAudioActNtyRequestVo dto) {
        log.info("??????????????????????????????AudioActDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/audioact/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("????????????????????????{}", response);
        String errorMsg = "????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_AUDIO_FAILED, response);
        return BaseResult.succeed("????????????????????????");
    }

    @Override
    public BaseResult<String> hb(Integer dbId) {
        log.info("????????????????????????dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "??????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.DEVICE_HEART_BEAT_FAILED, response);
        return BaseResult.succeed("??????????????????");
    }

    @Override
    public BaseResult<BurnStatesInfoVo> burnInfo(SvrRequestDto dto) {
        log.info("????????????????????????????????????SvrRequestDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/curburninfo/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("??????????????????????????????{}", exchange.getBody());
        String errorMsg = "??????????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_BURN_STATE_INFO_FAILED, response);
        BurnStatesInfoVo vo = JSON.parseObject(exchange.getBody(), BurnStatesInfoVo.class);

        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<RemoteDevListVo> remoteDevList(RemoteDevListDto dto) {
        log.info("???????????????????????????????????????RemotePointOffVo:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/remotedevlist/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("?????????????????????????????????{}", s);
        String errorMsg = "?????????????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_DEV_LIST_FAILED, response);
        RemoteDevListVo vo = JSON.parseObject(s, RemoteDevListVo.class);

        return BaseResult.succeed("?????????????????????????????????", vo);
    }

    @Override
    public BaseResult<RemoteChnListVo> remoteChnList(SvrRequestDto dto) {
        log.info("???????????????????????????????????????RemotePointOffVo:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotechnlist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("?????????????????????????????????{}", exchange.getBody());
        String errorMsg = "?????????????????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_CHN_LIST_FAILED, response);
        RemoteChnListVo vo = JSON.parseObject(exchange.getBody(), RemoteChnListVo.class);

        return BaseResult.succeed("????????????????????????????????????", vo);
    }

    @Override
    public BaseResult<String> remoteDev(RemoteDevDto dto) {
        log.info("??????/???????????????????????????RemoteDevDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/remotedev/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("??????/?????????????????????{}", s);
        String errorMsg = "??????/?????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_DEV_FAILED, response);

        return BaseResult.succeed("????????????");
    }

    @Override
    public BaseResult<SvrEntity> saveInfo(SvrEntity entity) {
        log.info("==========??????SVR???????????????{}", entity);
        if (!isRepeat(entity)) {
            throw new CuException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
        }
        String modelType = entity.getModelType();
        entity.setDevType(DeviceModelType.getEnum(modelType));
        svrMapper.insert(entity);
        return BaseResult.succeed("??????SVR??????", entity);
    }

    @Override
    public BaseResult<String> remotePointOff(RemotePointOffVo vo) {
        log.info("???????????????????????????RemotePointOffVo:{}", vo);
        SvrEntity entity = svrMapper.selectById(vo.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(vo.getDbId()));
        RemotePointDto pointDto = new RemotePointDto();
        pointDto.setType(1);
        RemotePoint remotePoint = new RemotePoint();
        remotePoint.setName(vo.getRpName());
        remotePoint.setUrl(vo.getUrl());
        pointDto.setRemotePoint(remotePoint);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/remotepoint/{ssid}/{ssno}", JSON.toJSONString(pointDto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("?????????????????????{}", response);
        String errorMsg = "?????????????????????:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_FAILED, response);
        return BaseResult.succeed("?????????????????????");
    }

    @Override
    public SvrEntity getBySsid(Integer ssid) {
        log.info("==============??????ssid??????svr??????ssid???{}", ssid);
        LambdaQueryWrapper<SvrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SvrEntity::getSsid, ssid);
        List<SvrEntity> list = svrMapper.selectList(wrapper);
        return list.get(0);
    }


    /**
     * ????????????IP???????????????
     *
     * @return
     */
    public boolean isRepeat(SvrEntity devEntity) {
        Integer id = devEntity.getId();
        String ip = devEntity.getIp();
        String name = devEntity.getName();
        LambdaQueryWrapper<SvrEntity> wrapper = new LambdaQueryWrapper<>();
        if (id == null) {
            wrapper.eq(SvrEntity::getIp, ip).or().eq(SvrEntity::getName, name);
            List<SvrEntity> devEntitiesInsert = svrMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(devEntitiesInsert)) {
                log.info("=============??????SVR???IP???????????????===============");
                return false;
            }
        } else {
            wrapper.eq(SvrEntity::getIp, ip).ne(SvrEntity::getId, id);
            List<SvrEntity> devEntitiesUpdate = svrMapper.selectList(wrapper);
            LambdaQueryWrapper<SvrEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SvrEntity::getName, name).ne(SvrEntity::getId, id);
            List<SvrEntity> devEntities = svrMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(devEntitiesUpdate) || CollectionUtil.isNotEmpty(devEntities)) {
                log.info("==================??????SVR???IP???????????????========================");
                return false;
            }
        }

        return true;
    }

    public SvrBasicParam getParam(Integer ssid) {
        SvrBasicParam param = new SvrBasicParam();
        Map<String, Long> paramMap = new HashMap<>();
        Long s = Long.valueOf(ssid);
        Long n = (long) NumGen.getNum();
        paramMap.put("ssid", s);
        paramMap.put("ssno", n);

        log.info("==========????????????ssid??????{},ssno??????{}", s, n);
        param.setParamMap(paramMap);
        param.setUrl(getUrl());

        return param;
    }

    public String getUrl() {
        String url = SVR_REQUEST_HEAD + kmProxy + SVR;
        return url;
    }

    /**
     * svr??????????????????????????????
     *
     * @param entity
     */
    private void check(SvrEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            throw new SvrException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
    }

}
