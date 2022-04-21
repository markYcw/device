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

    @Value("${zf.svrNtyUrl.server_addr:127.0.0.1:9000}")
    private String svrNtyUrl;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/device/notify";

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String SVR_REQUEST_HEAD = "http://";

    //svr2.0版本访问地址
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
        log.info("svr通知:{}", notify);
        JSONObject jsonObject = JSONObject.parseObject(notify);
        Integer type = (Integer) jsonObject.get("type");
        Integer ssid = (Integer) jsonObject.get("ssid");

        if (ssid != null) {
            NotifyHandler.getInstance().distributeMessages(ssid, DeviceType.SVR.getValue(), type, notify);
        }

    }

    @Override
    public BaseResult<SvrLoginVO> loginById(Integer id) {
        log.info("登录svr入参信息:{}", id);
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

        log.info("登录SVR中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        log.info("登录SVR中间件应答:{}", string);

        SvrLoginResponse response = JSON.parseObject(string, SvrLoginResponse.class);
        String errorMsg = "SVR登录失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_LOGIN_FAILED, response);
        entity.setSsid(response.getSsid());
        entity.setModifyTime(new Date());
        svrMapper.updateById(entity);

        SvrLoginVO loginVO = new SvrLoginVO();
        loginVO.setSsid(response.getSsid());
        return BaseResult.succeed(loginVO);
    }

    public Integer logById(Integer id) {
        log.info("登录svr入参信息:{}", id);
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

        log.info("登录SVR中间件入参信息:{}", JSON.toJSONString(request));
        String string = null;
        try {
            string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        } catch (RestClientException e) {
            log.info("=======登录svr失败{}", e);
            return 0;
        }
        log.info("登录SVR中间件应答:{}", string);

        SvrLoginResponse response = JSON.parseObject(string, SvrLoginResponse.class);
        if (response.getCode()==7){
            log.info("=======登录svr失败code=7");
            return 0;
        }
        String errorMsg = "SVR登录失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_LOGIN_FAILED, response);
        entity.setSsid(response.getSsid());
        svrMapper.updateById(entity);
        return response.getSsid();
    }

    @Override
    public BaseResult logoutById(Integer id) {
        log.info("根据ID登出svr接口入参{}", id);
        SvrEntity entity = svrMapper.selectById(id);
        check(entity);
        SvrBasicParam param = getParam(logById(id));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("登出svr响应{}", response);
        String errorMsg = "登出SVR失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_LOGOUT_FAILED, response);
        LambdaUpdateWrapper<SvrEntity> wrapper = new LambdaUpdateWrapper();
        wrapper.set(SvrEntity::getSsid, null)
                .set(SvrEntity::getModifyTime, new Date())
                .eq(SvrEntity::getId, id);
        svrMapper.update(null, wrapper);
        return BaseResult.succeed("登出SVR成功");
    }

    @Override
    public BaseResult<SvrCapVo> svrCap(Integer dbId) {
        log.info("获取SVR能力集接口入参{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/svrcap/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrCapResponse response = JSONObject.parseObject(exchange.getBody(), SvrCapResponse.class);
        log.info("获取SVR能力集响应{}", exchange.getBody());
        String errorMsg = "获取SVR能力集失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_CAP_FAILED, response);
        SvrCapVo svrCapVo = convert.convertTOSvrCapVo(response);
        return BaseResult.succeed("获取svr能力集成功", svrCapVo);
    }

    @Override
    public void synData() {
        log.info("=========服务启动同步svr定时任务开始");
        LambdaQueryWrapper<SvrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SvrEntity::getDc, 1);
        List<SvrEntity> list = svrMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            Iterator<SvrEntity> iterator = list.iterator();
            while (iterator.hasNext()) {
                SvrEntity next = iterator.next();
                Integer ssid = this.logById(next.getId());
                if(ssid>0){
                    BaseResult<SvrCapVo> result = this.svrCap(next.getId());
                    String modelType = result.getData().getSvrModel().substring(0, 7);
                    next.setModelType(modelType);
                    next.setDc(0);
                    svrMapper.updateById(next);
                }else {
                    next.setPort(9765);
                    next.setWebPort(9766);
                    next.setDc(2);
                    svrMapper.updateById(next);
                    Integer rid =  this.logById(next.getId());
                    if(rid>0){
                        BaseResult<SvrCapVo> result = this.svrCap(next.getId());
                        String modelType = result.getData().getSvrModel().substring(0, 7);
                        next.setModelType(modelType);
                    }else {
                        next.setModelType("其他");
                    }
                    svrMapper.updateById(next);
                }
            }
        }
    }

    @Override
    public BaseResult<String> svrTime(Integer dbId) {
        log.info("获取SVR时间接口入参{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/svrtime/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrTimeResponse response = JSONObject.parseObject(exchange.getBody(), SvrTimeResponse.class);
        log.info("获取SVR时间接口响应{}", exchange.getBody());
        String errorMsg = "获取SVR时间失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_TIME_FAILED, response);
        return BaseResult.succeed("获取SVR时间成功", response.getTime());
    }

    @Override
    public BaseResult<String> searchDev(Integer dbId) {
        log.info("搜素编解码设备接口入参{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/searchdev/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("搜素编解码设备接口响应{}", exchange.getBody());
        String errorMsg = "搜索编解码设备失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_SEARCH_DEV_FAILED, response);
        return BaseResult.succeed("搜索编解码设备成功");
    }

    @Override
    public BaseResult<List<EnChnListVo>> enChnList(Integer dbId) {
        log.info("获取编码通道列表接口入参{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/encchnlist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("===============获取编码设备回复:{}", exchange.getBody());
        ChnListExtendsResponse response = JSONObject.parseObject(exchange.getBody(), ChnListExtendsResponse.class);
        log.info("获取编码通道列表接口响应{}", exchange.getBody());
        String errorMsg = "获取编码通道列表失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_CHN_LIST_FAILED, response);
        List<EnChnListVo> listVos = response.getChnList().stream().map(enChnList -> convert.convertToEnChnListVo(enChnList)).collect(Collectors.toList());
        return BaseResult.succeed("获取编码通道列表成功", listVos);
    }

    @Override
    public BaseResult<String> enChn(EnChnDto enChnDto) {
        log.info("添加/删除编码通道接口入参EnChnDto:{}", enChnDto);
        SvrEntity entity = svrMapper.selectById(enChnDto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(enChnDto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/encchn/{ssid}/{ssno}", JSON.toJSONString(enChnDto), String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(s, SvrResponse.class);
        log.info("添加/删除编码通道接口响应{}", response);
        String errorMsg = "添加/删除编码通道失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_CHN_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult<CpResetVo> enCpReset(GetIpcItemRequestVo dto) {
        log.info("获取编码器的预置位接口入参EnCpResetDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/encpreset/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        CpResetResponse response = JSON.parseObject(s, CpResetResponse.class);
        log.info("获取编码器的预置位接口响应{}", response);
        String errorMsg = "获取编码器的预置位失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_CP_RESET_FAILED, response);
        CpResetVo cpResetVo = convert.convertToCpResetVo(response);
        return BaseResult.succeed("获取编码器的预置位成功", cpResetVo);
    }

    @Override
    public BaseResult<String> CpReset(CpResetDto dto) {
        log.info("修改编码器预置位接口入参CpResetDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/encpreset/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("修改编码器预置位接口响应{}", exchange.getBody());
        String errorMsg = "修改编码器预置位失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_CP_RESET_FAILED, response);
        return BaseResult.succeed("修改编码器预置位成功");
    }

    @Override
    public BaseResult<DeChnListVo> deChnList(Integer dbId) {
        log.info("获取解码通道列表接口入参:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/decchnlist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取解码通道列表接口响应{}", exchange.getBody());
        DecChnListResponse response = JSON.parseObject(exchange.getBody(), DecChnListResponse.class);
        log.info("获取解码通道列表接口响应{}", exchange.getBody());
        String errorMsg = "获取解码通道列表失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DE_CHN_LIST_FAILED, response);
        DeChnListVo deChnListVo = convert.convertToDeChnListVo(response);
        return BaseResult.succeed("获取解码通道列表成功", deChnListVo);
    }

    @Override
    public BaseResult<String> deChn(DeChnDto dto) {
        log.info("添加/删除解码通道接口入参DeChnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/decchn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("添加/删除解码通道接口响应{}", response);
        String errorMsg = "添加/删除解码通道失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DE_CHN_FAILED, response);
        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult<DecParamVo> decParam(DecParamDto dto) {
        log.info("获取解码参数接口入参DecParamDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/decparam/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        DecParamResponse response = JSON.parseObject(s, DecParamResponse.class);
        log.info("获取解码参数接口响应{}", response);
        String errorMsg = "获取解码参数失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DEC_PARAM_FAILED, response);
        DecParamVo decParamVo = convert.convertToDecParamVo(response);
        return BaseResult.succeed("获取解码参数成功", decParamVo);
    }

    @Override
    public BaseResult<String> enDeParam(EnDecParamDto dto) {
        log.info("设置解码参数接口入参EnDecParamDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/decparam/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("设置解码参数接口响应{}", exchange.getBody());
        String errorMsg = "设置解码参数失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_EN_DEC_PARAM_FAILED, response);
        return BaseResult.succeed("设置解码参数成功");
    }

    @Override
    public BaseResult<String> ptz(PtzCtrlRequestVo dto) {
        log.info("ptz控制接口入参PtzDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/ptz/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("ptz控制接口响应{}", exchange.getBody());
        String errorMsg = "ptz控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_PTZ_FAILED, response);
        return BaseResult.succeed("ptz控制成功");
    }

    @Override
    public BaseResult<String> remotePoint(RemotePointOnVo dto) {
        log.info("启用远程点接口入参RemotePointOnVo:{}", dto);
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
        log.info("启用远程点接口响应{}", response);
        String errorMsg = "启用远程点失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_FAILED, response);
        return BaseResult.succeed("启用远程点成功");
    }

    @Override
    public BaseResult<RemoteCfgVo> remoteCfg(Integer dbId) {
        log.info("获取远程点配置接口入参dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotecfg/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        RemoteCfgVoResponse response = JSON.parseObject(exchange.getBody(), RemoteCfgVoResponse.class);
        String errorMsg = "获取远程点配置失败:{},{},{}";
        log.info("获取远程点配置接口响应{}", exchange.getBody());
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_CFG_FAILED, response);
        RemoteCfgVo remoteCfgVo = convert.convertToRemoteCfgVo(response.getRemoteCfg());
        return BaseResult.succeed("获取远程点配置成功", remoteCfgVo);
    }

    @Override
    public BaseResult<String> remotePutCfg(RemotePutCfgDto dto) {
        log.info("修改远程点配置接口入参RemotePutCfgDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String obj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(obj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotecfg/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("修改远程点配置接口响应{}", exchange.getBody());
        String errorMsg = "修改远程点配置失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_PUT_CFG_FAILED, response);
        return BaseResult.succeed("修改远程点配置成功");
    }

    @Override
    public BaseResult<String> dual(StartDualRequestVo dto) {
        log.info("发送双流接口入参DualDto:{}", dto);
        Integer dbId = dto.getDbId();
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/dual/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("发送双流接口响应{}", exchange.getBody());
        String errorMsg = "发送双流失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DUAL_FAILED, response);
        return BaseResult.succeed("发送双流成功");
    }

    @Override
    public BaseResult<String> burn(BurnDto dto) {
        log.info("刻录控制接口入参BurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/burn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("刻录控制接口响应{}", response);
        String errorMsg = "刻录控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_BURN_FAILED, response);
        return BaseResult.succeed("刻录控制成功");
    }

    @Override
    public BaseResult<String> reBurn(SupplementBurnVo dto) {
        log.info("补刻接口入参ReBurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/reburn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("补刻接口响应{}", response);
        String errorMsg = "补刻失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_RE_BURN_FAILED, response);
        return BaseResult.succeed("补刻成功");
    }

    @Override
    public BaseResult<String> appendBurn(AppendBurnDto dto) {
        log.info("追加刻录任务接口入参AppendBurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/appendburn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("追加刻录任务响应{}", response);
        String errorMsg = "追加刻录任务失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_APPEND_BURN_FAILED, response);
        return BaseResult.succeed("追加刻录任务成功");
    }

    @Override
    public BaseResult<String> createBurn(CreateBurnRequestVo dto) {
        log.info("新建刻录任务接口入参CreateBurnDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        CreateBurnRequestDto create = new CreateBurnRequestDto();
        create.setStartTime(DateUtils.getDateString(dto.getStartTime()));
        create.setEndTime(DateUtils.getDateString(dto.getEndTime()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/createburn/{ssid}/{ssno}", JSON.toJSONString(create), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("新建刻录任务响应{}", response);
        String errorMsg = "新建刻录任务失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_CREATE_BURN_FAILED, response);
        return BaseResult.succeed("新建刻录任务成功");
    }

    @Override
    public BaseResult<GetBurnTaskResponseVo> burnTaskList(GetBurnTaskRequestVo dto) {
        log.info("获取刻录任务接口入参BurnTaskListDto:{}", dto);
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
        String errorMsg = "获取刻录任务失败:{},{},{}";
        log.info("获取刻录任务响应{}", response);
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_BURN_TASK_LIST_FAILED, response);
        GetBurnTaskResponseVo vo = convert.convertTOBurnTaskVo(response);
        return BaseResult.succeed("获取刻录任务成功", vo);
    }

    @Override
    public BaseResult<String> dvdDoor(DvdDoorCtrlVo dto) {
        log.info("DVD仓门控制接口入参DvdDoorDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/dvddoor/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("DVD仓门控制接口响应{}", response);
        String errorMsg = "DVD仓门控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_DVD_DOOR_FAILED, response);
        return BaseResult.succeed("DVD仓门控制成功");
    }

    @Override
    public BaseResult<RecListResponse> recList(QueryRecVo dto) {
        log.info("查询录像接口入参RecListDto:{}", dto);
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
        log.info("查询录像接口响应{}", response);
        String errorMsg = "查询录像失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REC_LIST_FAILED, response);
        RecListResponse recListResponse = JSON.parseObject(s, RecListResponse.class);
        return BaseResult.succeed("查询录像成功", recListResponse);
    }

    @Override
    public BaseResult<GetSvrComposePicResponseVo> getMerge(Integer dbId) {
        log.info("获取画面合成接口入参dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/merge/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        GetMergeResponse response = JSON.parseObject(exchange.getBody(), GetMergeResponse.class);
        log.info("获取画面合成接口响应{}", exchange.getBody());
        String errorMsg = "获取画面合成失败:{},{},{}";
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
        return BaseResult.succeed("获取画面合成成功", vo);
    }

    @Override
    public BaseResult<String> merge(SetSvrComposePicVo dto) {
        log.info("设置画面合成接口入参MergeInfoDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/merge/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("设置画面合成接口响应{}", exchange.getBody());
        String errorMsg = "设置画面合成失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_MERGE_FAILED, response);
        return BaseResult.succeed("设置画面合成成功");
    }

    @Override
    public BaseResult<GetOsdVo> getOsd(Integer dbId) {
        log.info("获取画面叠加接口入参dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/osd/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取画面叠加响应{}", exchange.getBody());
        GetOsdResponse response = JSON.parseObject(exchange.getBody(), GetOsdResponse.class);
        String errorMsg = "获取画面叠加失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_GET_OSD_FAILED, response);
        GetOsdVo vo = convert.convertToGetOsdVo(response);
        return BaseResult.succeed("获取画面叠加成功", vo);
    }

    @Override
    public BaseResult<String> osd(OsdSetVo dto) {
        log.info("设置画面叠加接口入参OsdDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/osd/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("设置画面叠加响应{}", exchange.getBody());
        String errorMsg = "设置画面叠加失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_OSD_FAILED, response);
        return BaseResult.succeed("设置画面叠加成功");
    }

    @Override
    public BaseResult<String> audioAct(SetAudioActNtyRequestVo dto) {
        log.info("语音激励控制接口入参AudioActDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/audioact/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("语音激励控制响应{}", response);
        String errorMsg = "语音激励控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_AUDIO_FAILED, response);
        return BaseResult.succeed("语音激励控制成功");
    }

    @Override
    public BaseResult<String> hb(Integer dbId) {
        log.info("发送心跳接口入参dbId:{}", dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = getParam(logById(dbId));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "发送心跳失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.DEVICE_HEART_BEAT_FAILED, response);
        return BaseResult.succeed("发送心跳成功");
    }

    @Override
    public BaseResult<BurnStatesInfoVo> burnInfo(SvrRequestDto dto) {
        log.info("获取当前刻录状态接口入参SvrRequestDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/curburninfo/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("获取当前刻录状态响应{}", exchange.getBody());
        String errorMsg = "获取当前刻录状态失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_BURN_STATE_INFO_FAILED, response);
        BurnStatesInfoVo vo = JSON.parseObject(exchange.getBody(), BurnStatesInfoVo.class);

        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<RemoteDevListVo> remoteDevList(RemoteDevListDto dto) {
        log.info("获取远程点设备列表接口入参RemotePointOffVo:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/remotedevlist/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("获取远程点设备列表响应{}", s);
        String errorMsg = "获取远程点设备列表失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_DEV_LIST_FAILED, response);
        RemoteDevListVo vo = JSON.parseObject(s, RemoteDevListVo.class);

        return BaseResult.succeed("获取远程点设备列表成功", vo);
    }

    @Override
    public BaseResult<RemoteChnListVo> remoteChnList(SvrRequestDto dto) {
        log.info("获取远程点通道列表接口入参RemotePointOffVo:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotechnlist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        log.info("获取远程点通道列表响应{}", exchange.getBody());
        String errorMsg = "获取远程点通道列表失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_CHN_LIST_FAILED, response);
        RemoteChnListVo vo = JSON.parseObject(exchange.getBody(), RemoteChnListVo.class);

        return BaseResult.succeed("获取远程点通道列表表成功", vo);
    }

    @Override
    public BaseResult<String> remoteDev(RemoteDevDto dto) {
        log.info("添加/删除远程点接口入参RemoteDevDto:{}", dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = getParam(logById(dto.getDbId()));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/remotedev/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        log.info("添加/删除远程点响应{}", s);
        String errorMsg = "添加/删除远程点失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_DEV_FAILED, response);

        return BaseResult.succeed("操作成功");
    }

    @Override
    public BaseResult<SvrEntity> saveInfo(SvrEntity entity) {
        log.info("==========保存SVR接口入参：{}", entity);
        if (!isRepeat(entity)) {
            throw new CuException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
        }
        String modelType = entity.getModelType();
        entity.setDevType(DeviceModelType.getEnum(modelType));
        svrMapper.insert(entity);
        return BaseResult.succeed("保存SVR成功", entity);
    }

    @Override
    public BaseResult<String> remotePointOff(RemotePointOffVo vo) {
        log.info("停用远程点接口入参RemotePointOffVo:{}", vo);
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
        log.info("停用远程点响应{}", response);
        String errorMsg = "停用远程点失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_REMOTE_FAILED, response);
        return BaseResult.succeed("停用远程点成功");
    }

    @Override
    public SvrEntity getBySsid(Integer ssid) {
        log.info("==============根据ssid获取svr入参ssid：{}", ssid);
        LambdaQueryWrapper<SvrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SvrEntity::getSsid, ssid);
        List<SvrEntity> list = svrMapper.selectList(wrapper);
        return list.get(0);
    }


    /**
     * 对名称和IP做唯一校验
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
                log.info("=============添加SVR时IP或名称重复===============");
                return false;
            }
        } else {
            wrapper.eq(SvrEntity::getIp, ip).ne(SvrEntity::getId, id);
            List<SvrEntity> devEntitiesUpdate = svrMapper.selectList(wrapper);
            LambdaQueryWrapper<SvrEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SvrEntity::getName, name).ne(SvrEntity::getId, id);
            List<SvrEntity> devEntities = svrMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(devEntitiesUpdate) || CollectionUtil.isNotEmpty(devEntities)) {
                log.info("==================修改SVR时IP或名称重复========================");
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

        log.info("==========此次请求ssid为：{},ssno为：{}", s, n);
        param.setParamMap(paramMap);
        param.setUrl(getUrl());

        return param;
    }

    public String getUrl() {
        String url = SVR_REQUEST_HEAD + kmProxy + SVR;
        return url;
    }

    /**
     * svr非空校验以及登录校验
     *
     * @param entity
     */
    private void check(SvrEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            throw new SvrException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
    }

}
