package com.kedacom.device.core.service.impl;

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
import com.kedacom.device.core.exception.SvrException;
import com.kedacom.device.core.mapper.SvrMapper;
import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyHandler;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.device.core.utils.*;
import com.kedacom.device.svr.SvrResponse;
import com.kedacom.device.svr.request.SvrLoginRequest;
import com.kedacom.device.svr.response.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.vo.*;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/6 10:50
 * @description
 */
@Slf4j
@Service("svrService")
public class SvrServiceImpl extends ServiceImpl<SvrMapper,SvrEntity> implements SvrService {

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Autowired
    private SvrMapper svrMapper;

    @Autowired
    private SvrConvert convert;

    @Autowired
    private SvrUrlFactory factory;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private SvrBasicTool tool;

    @Value("${zf.svrNtyUrl.server_addr:127.0.0.1:9000}")
    private String svrNtyUrl;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/svr/svrNotify";

    @Override
    public BaseResult<BasePage<SvrEntity>> pageQuery(SvrPageQueryDTO queryDTO) {
        Page<SvrEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<SvrEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(queryDTO.getIp())){
            queryWrapper.like(SvrEntity::getIp,queryDTO.getIp());
        }
        if(!StringUtils.isEmpty(queryDTO.getName())){
            queryWrapper.like(SvrEntity::getName,queryDTO.getName());
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

        if(ssid !=null){
            NotifyHandler.getInstance().distributeMessages(ssid,DeviceType.SVR.getValue(),type,notify);
        }

    }

    @Override
    public BaseResult<SvrLoginVO> loginById(Integer id) {
        log.info("登录svr入参信息:{}", id);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        SvrEntity entity = svrMapper.selectById(id);
        if(entity == null){
            throw new SvrException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        SvrLoginRequest request = convert.convertToSvrLoginRequest(entity);
        String ntyUrl = REQUEST_HEAD + svrNtyUrl + NOTIFY_URL;
        request.setNtyUrl(ntyUrl);
        String url = factory.geturl(entity.getDevType());
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

    @Override
    public BaseResult logoutById(Integer id) {
        log.info("根据ID登出svr接口入参{}",id);
        SvrEntity entity = svrMapper.selectById(id);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "登出SVR失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg, DeviceErrorEnum.SVR_LOGOUT_FAILED, response);
        LambdaUpdateWrapper<SvrEntity> wrapper = new LambdaUpdateWrapper();
        wrapper.set(SvrEntity::getSsid,null)
                .set(SvrEntity::getModifyTime,new Date())
                .eq(SvrEntity::getId,id);
        svrMapper.update(null,wrapper);
        return BaseResult.succeed("登出SVR成功");
    }

    @Override
    public BaseResult<SvrCapVo> svrCap(Integer dbId) {
        log.info("获取SVR能力集接口入参{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/svrcap/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrCapResponse response = JSONObject.parseObject(exchange.getBody(), SvrCapResponse.class);
        String errorMsg = "获取SVR能力集失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_CAP_FAILED,response);
        SvrCapVo svrCapVo = convert.convertTOSvrCapVo(response);
        return BaseResult.succeed("获取svr能力集成功",svrCapVo);
    }

    @Override
    public BaseResult<SvrTimeVo> svrTime(Integer dbId) {
        log.info("获取SVR时间接口入参{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/svrtime/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrTimeResponse response = JSONObject.parseObject(exchange.getBody(), SvrTimeResponse.class);
        String errorMsg = "获取SVR时间失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_TIME_FAILED,response);
        SvrTimeVo svrTimeVo = convert.convertTOSvrTimeVo(response);
        return BaseResult.succeed("获取SVR时间成功",svrTimeVo);
    }

    @Override
    public BaseResult<String> searchDev(Integer dbId) {
        log.info("搜素编解码设备接口入参{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/searchdev/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "搜索编解码设备失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_SEARCH_DEV_FAILED,response);
        return BaseResult.succeed("搜索编解码设备成功");
    }

    @Override
    public BaseResult<List<EnChnListVo>> enChnList(Integer dbId) {
        log.info("获取编码通道列表接口入参{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/encchnlist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("===============获取编码设备回复:{}",exchange.getBody());
        ChnListExtendsResponse response = JSONObject.parseObject(exchange.getBody(), ChnListExtendsResponse.class);
        String errorMsg = "获取编码通道列表失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_EN_CHN_LIST_FAILED,response);
        List<EnChnListVo> listVos = response.getChnList().stream().map(enChnList -> convert.convertToEnChnListVo(enChnList)).collect(Collectors.toList());
        return BaseResult.succeed("获取编码通道列表成功",listVos);
    }

    @Override
    public BaseResult<String> enChn(EnChnDto enChnDto) {
        log.info("添加/删除编码通道接口入参EnChnDto:{}",enChnDto);
        SvrEntity entity = svrMapper.selectById(enChnDto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/encchn/{ssid}/{ssno}", JSON.toJSONString(enChnDto), String.class, param.getParamMap());
        SvrResponse response = JSONObject.parseObject(s, SvrResponse.class);
        String errorMsg = "添加/删除编码通道失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_EN_CHN_FAILED,response);
        return BaseResult.succeed("添加/删除编码通道成功");
    }

    @Override
    public BaseResult<CpResetVo>  enCpReset(GetIpcItemRequestVo dto) {
        log.info("获取编码器的预置位接口入参EnCpResetDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/encpreset/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        CpResetResponse response = JSON.parseObject(s, CpResetResponse.class);
        String errorMsg = "获取编码器的预置位失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_EN_CP_RESET_FAILED,response);
        CpResetVo cpResetVo = convert.convertToCpResetVo(response);
        return BaseResult.succeed("获取编码器的预置位成功",cpResetVo);
    }

    @Override
    public BaseResult<String> CpReset(CpResetDto dto) {
        log.info("修改编码器预置位接口入参CpResetDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/encpreset/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "修改编码器预置位失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_CP_RESET_FAILED,response);
        return BaseResult.succeed("修改编码器预置位成功");
    }

    @Override
    public BaseResult<DeChnListVo> deChnList(Integer dbId) {
        log.info("获取解码通道列表接口入参:{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/decchnlist/{ssid}/{ssno}", null, String.class, param.getParamMap());
        DecChnListResponse response = JSON.parseObject(s, DecChnListResponse.class);
        String errorMsg = "获取解码通道列表失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_DE_CHN_LIST_FAILED,response);
        DeChnListVo deChnListVo = convert.convertToDeChnListVo(response.getChnList());
        return BaseResult.succeed("获取解码通道列表成功",deChnListVo);
    }

    @Override
    public BaseResult<String> deChn(DeChnDto dto) {
        log.info("添加/删除解码通道接口入参DeChnDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/decchn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "添加/删除解码通道失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_DE_CHN_FAILED,response);
        return BaseResult.succeed("添加/删除解码通道成功");
    }

    @Override
    public BaseResult<DecParamVo> decParam(DecParamDto dto) {
        log.info("获取解码参数接口入参DecParamDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/decparam/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        DecParamResponse response = JSON.parseObject(s, DecParamResponse.class);
        String errorMsg = "获取解码参数失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_DEC_PARAM_FAILED,response);
        DecParamVo decParamVo = convert.convertToDecParamVo(response);
        return BaseResult.succeed("获取解码参数成功",decParamVo);
    }

    @Override
    public BaseResult<String> enDeParam(EnDecParamDto dto) {
        log.info("设置解码参数接口入参EnDecParamDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/decparam/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "设置解码参数失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_EN_DEC_PARAM_FAILED,response);
        return BaseResult.succeed("设置解码参数成功");
    }

    @Override
    public BaseResult<String> ptz(PtzCtrlRequestVo dto) {
        log.info("ptz控制接口入参PtzDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/ptz/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "ptz控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_PTZ_FAILED,response);
        return BaseResult.succeed("ptz控制成功");
    }

    @Override
    public BaseResult<String> remotePoint(RemotePointDto dto) {
        log.info("启用/停止远程点接口入参RemotePointDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/remotepoint/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "启用/停止远程点失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_REMOTE_FAILED,response);
        return BaseResult.succeed("启用/停止远程点成功");
    }

    @Override
    public BaseResult<RemoteCfgVo> remoteCfg(Integer dbId) {
        log.info("获取远程点配置接口入参dbId:{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotecfg/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        RemoteCfgVoResponse response = JSON.parseObject(exchange.getBody(), RemoteCfgVoResponse.class);
        String errorMsg = "获取远程点配置失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_REMOTE_CFG_FAILED,response);
        RemoteCfgVo remoteCfgVo = convert.convertToRemoteCfgVo(response);
        return BaseResult.succeed("获取远程点配置成功",remoteCfgVo);
    }

    @Override
    public BaseResult<String> remotePutCfg(RemotePutCfgDto dto) {
        log.info("修改远程点配置接口入参RemotePutCfgDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String obj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(obj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/remotecfg/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "修改远程点配置失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_REMOTE_PUT_CFG_FAILED,response);
        return BaseResult.succeed("修改远程点配置成功");
    }

    @Override
    public BaseResult<String> dual(StartDualRequestVo dto) {
        log.info("发送双流接口入参DualDto:{}",dto);
        Integer dbId = dto.getDbId();
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/dual/{ssid}/{ssno}", HttpMethod.PUT, httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "发送双流失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_DUAL_FAILED,response);
        return BaseResult.succeed("发送双流成功");
    }

    @Override
    public BaseResult<String> burn(BurnDto dto) {
        log.info("刻录控制接口入参BurnDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/burn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "刻录控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_BURN_FAILED,response);
        return BaseResult.succeed("刻录控制成功");
    }

    @Override
    public BaseResult<String> reBurn(SupplementBurnVo dto) {
        log.info("补刻接口入参ReBurnDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/reburn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "补刻失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_RE_BURN_FAILED,response);
        return BaseResult.succeed("补刻成功");
    }

    @Override
    public BaseResult<String> appendBurn(AppendBurnDto dto) {
        log.info("追加刻录任务接口入参AppendBurnDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/appendburn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "追加刻录任务失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_APPEND_BURN_FAILED,response);
        return BaseResult.succeed("追加刻录任务成功");
    }

    @Override
    public BaseResult<String> createBurn(CreateBurnRequestVo dto) {
        log.info("新建刻录任务接口入参CreateBurnDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/createburn/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "新建刻录任务失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_CREATE_BURN_FAILED,response);
        return BaseResult.succeed("新建刻录任务成功");
    }

    @Override
    public BaseResult<GetBurnTaskResponseVo> burnTaskList(GetBurnTaskRequestVo dto) {
        log.info("获取刻录任务接口入参BurnTaskListDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/burntasklist/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        BurnTaskResponse response = JSON.parseObject(s, BurnTaskResponse.class);
        String errorMsg = "获取刻录任务失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_BURN_TASK_LIST_FAILED,response);
        GetBurnTaskResponseVo vo = convert.convertTOBurnTaskVo(response);
        return BaseResult.succeed("获取刻录任务成功",vo);
    }

    @Override
    public BaseResult<String> dvdDoor(DvdDoorCtrlVo dto) {
        log.info("DVD仓门控制接口入参DvdDoorDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/dvddoor/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "DVD仓门控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_DVD_DOOR_FAILED,response);
        return BaseResult.succeed("DVD仓门控制成功");
    }

    @Override
    public BaseResult<RecListVo> recList(QueryRecVo dto) {
        log.info("查询录像接口入参RecListDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/reclist/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        RecListResponse response = JSON.parseObject(s, RecListResponse.class);
        String errorMsg = "查询录像失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_REC_LIST_FAILED,response);
        RecListVo vo = convert.convertToRecListVo(response);
        return BaseResult.succeed("查询录像成功",vo);
    }

    @Override
    public BaseResult<GetMergeVo> getMerge(Integer dbId) {
        log.info("获取画面合成接口入参dbId:{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/merge/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        GetMergeResponse response = JSON.parseObject(exchange.getBody(), GetMergeResponse.class);
        String errorMsg = "获取画面合成失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_GET_MERGE_FAILED,response);
        GetMergeVo vo = convert.convertToGetMergeVo(response);
        return BaseResult.succeed("获取画面合成成功",vo);
    }

    @Override
    public BaseResult<String> merge(SetSvrComposePicVo dto) {
        log.info("设置画面合成接口入参MergeInfoDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/merge/{ssid}/{ssno}",HttpMethod.PUT,httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "设置画面合成失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_MERGE_FAILED,response);
        return BaseResult.succeed("设置画面合成成功");
    }

    @Override
    public BaseResult<GetOsdVo> getOsd(Integer dbId) {
        log.info("获取画面叠加接口入参dbId:{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/osd/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        GetOsdResponse response = JSON.parseObject(exchange.getBody(), GetOsdResponse.class);
        String errorMsg = "获取画面叠加失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_GET_OSD_FAILED,response);
        GetOsdVo vo = convert.convertToGetOsdVo(response);
        return BaseResult.succeed("获取画面叠加成功",vo);
    }

    @Override
    public BaseResult<String> osd(OsdSetVo dto) {
        log.info("设置画面叠加接口入参OsdDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String jsonObj = JSON.toJSONString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObj, remoteRestTemplate.getHttpHeaders());
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/osd/{ssid}/{ssno}",HttpMethod.PUT,httpEntity, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "设置画面叠加失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_OSD_FAILED,response);
        return BaseResult.succeed("设置画面叠加成功");
    }

    @Override
    public BaseResult<String> audioAct(SetAudioActNtyRequestVo dto) {
        log.info("语音激励控制接口入参AudioActDto:{}",dto);
        SvrEntity entity = svrMapper.selectById(dto.getDbId());
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/audioact/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(s, SvrResponse.class);
        String errorMsg = "语音激励控制失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.SVR_AUDIO_FAILED,response);
        return BaseResult.succeed("语音激励控制成功");
    }

    @Override
    public BaseResult<String> hb(Integer dbId) {
        log.info("发送心跳接口入参dbId:{}",dbId);
        SvrEntity entity = svrMapper.selectById(dbId);
        check(entity);
        SvrBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        SvrResponse response = JSON.parseObject(exchange.getBody(), SvrResponse.class);
        String errorMsg = "发送心跳失败:{},{},{}";
        responseUtil.handleSvrRes(errorMsg,DeviceErrorEnum.DEVICE_HEART_BEAT_FAILED,response);
        return BaseResult.succeed("发送心跳成功");
    }

    @Override
    public BaseResult<SvrEntity> saveInfo(SvrEntity entity) {
        String modelType = entity.getModelType();
        entity.setDevType(DeviceModelType.getEnum(modelType));
        svrMapper.insert(entity);
        return BaseResult.succeed("保存SVR成功",entity);
    }

    /**
     * svr非空校验以及登录校验
     * @param entity
     */
    private void check(SvrEntity entity){
        if(ObjectUtils.isEmpty(entity)){
            throw new SvrException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if(entity.getSsid()==null){
            throw new SvrException(DeviceErrorEnum.DEVICE_NOT_LOGIN);
        }
    }

}
