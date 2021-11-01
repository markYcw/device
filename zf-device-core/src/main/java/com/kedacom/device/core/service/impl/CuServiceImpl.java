package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.CuPageQueryDTO;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.dto.DevGroupsDto;
import com.kedacom.cu.dto.SelectTreeDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.DomainsVo;
import com.kedacom.cu.vo.LocalDomainVo;
import com.kedacom.cu.vo.TimeVo;
import com.kedacom.cu.vo.ViewTreesVo;
import com.kedacom.device.core.basicParam.CuBasicParam;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.CuConvert;
import com.kedacom.device.core.basicParam.SvrBasicParam;
import com.kedacom.device.core.exception.CuException;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyHandler;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.utils.*;
import com.kedacom.device.cu.CuResponse;
import com.kedacom.device.cu.request.CuLoginRequest;
import com.kedacom.device.cu.response.CuLoginResponse;
import com.kedacom.device.svr.SvrResponse;
import com.kedacom.device.svr.response.*;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.vo.*;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
 * @date 2021/10/29 16:04
 * @description
 */
@Slf4j
@Service("cuService")
public class CuServiceImpl extends ServiceImpl<CuMapper, CuEntity> implements CuService {

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Autowired
    private CuMapper cuMapper;

    @Autowired
    private CuConvert convert;

    @Autowired
    private CuUrlFactory factory;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private CuBasicTool tool;

    @Value("${zf.cuNtyUrl.server_addr:127.0.0.1:9000}")
    private String cuNtyUrl;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/cu/cuNotify";

    @Override
    public BaseResult<BasePage<CuEntity>> pageQuery(CuPageQueryDTO queryDTO) {
        Page<CuEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<CuEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(queryDTO.getIp())){
            queryWrapper.like(CuEntity::getIp,queryDTO.getIp());
        }
        if(!StringUtils.isEmpty(queryDTO.getName())){
            queryWrapper.like(CuEntity::getName,queryDTO.getName());
        }

        Page<CuEntity> platformEntityPage = cuMapper.selectPage(page, queryWrapper);
        List<CuEntity> records = platformEntityPage.getRecords();

        BasePage<CuEntity> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(records);
        return BaseResult.succeed(basePage);
    }

    @Override
    public void cuNotify(String notify) {
        log.info("cu通知:{}", notify);
        JSONObject jsonObject = JSONObject.parseObject(notify);
        Integer type = (Integer) jsonObject.get("type");
        Integer ssid = (Integer) jsonObject.get("ssid");

        if(ssid !=null){
            NotifyHandler.getInstance().distributeMessages(ssid,DeviceType.CU2.getValue(),type,notify);
        }

    }

    @Override
    public BaseResult<String> loginById(CuRequestDto dto) {
        log.info("登录cu入参信息:{}", dto.getDbId());
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        if(entity == null){
            throw new CuException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        CuLoginRequest request = convert.convertToCuLoginRequest(entity);
        String ntyUrl = REQUEST_HEAD + cuNtyUrl + NOTIFY_URL;
        request.setNtyUrl(ntyUrl);
        String url = factory.geturl(entity.getType());
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());

        log.info("登录cu中间件入参信息:{}", JSON.toJSONString(request));
        String string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        log.info("登录cu中间件应答:{}", string);

        CuLoginResponse response = JSON.parseObject(string, CuLoginResponse.class);
        String errorMsg = "cu登录失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_LOGIN_FAILED, response);
        entity.setSsid(response.getSsid());
        entity.setModifyTime(new Date());
        cuMapper.updateById(entity);
        return BaseResult.succeed("登录监控平台成功");
    }

    @Override
    public BaseResult logoutById(CuRequestDto dto) {
        log.info("根据ID登出cu接口入参{}",dto.getDbId());
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "登出cu失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_LOGOUT_FAILED, response);
        LambdaUpdateWrapper<CuEntity> wrapper = new LambdaUpdateWrapper();
        wrapper.set(CuEntity::getSsid,null)
                .set(CuEntity::getModifyTime,new Date())
                .eq(CuEntity::getId,dto.getDbId());
        cuMapper.update(null,wrapper);
        return BaseResult.succeed("登出cu成功");
    }

    @Override
    public BaseResult<LocalDomainVo> localDomain(CuRequestDto dto) {
        log.info("获取平台域信息接口入参{}",dto);
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/localdomain/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取平台域信息中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "获取平台域信息失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_LOCAL_DOMAIN_FAILED,response);
        LocalDomainVo vo = JSON.parseObject(exchange.getBody(), LocalDomainVo.class);
        return BaseResult.succeed("获取平台域信息成功",vo);
    }

    @Override
    public BaseResult<DomainsVo> domains(CuRequestDto dto) {
        log.info("获取域链表接口入参{}",dto.getDbId());
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/domains/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取域链表中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "获取域链表失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_DOMAINS_FAILED,response);
        DomainsVo vo = JSON.parseObject(exchange.getBody(), DomainsVo.class);
        return BaseResult.succeed("获取域链表成功",vo);
    }

    @Override
    public BaseResult<TimeVo> time(CuRequestDto dto) {
        log.info("获取平台时间接口入参{}",dto.getDbId());
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/time/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取平台时间中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "获取平台时间失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_TIME_FAILED,response);
        TimeVo vo = JSON.parseObject(exchange.getBody(), TimeVo.class);
        return BaseResult.succeed("获取平台时间成功",vo);
    }

    @Override
    public BaseResult<String> hb(Integer dbId) {
        return null;
    }

    @Override
    public BaseResult<ViewTreesVo> viewTrees(CuRequestDto dto) {
        log.info("获取多视图设备树接口入参{}",dto.getDbId());
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/viewtrees/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取多视图设备树中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "获取多视图设备树失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_VIEW_TREES_FAILED,response);
        ViewTreesVo vo = JSON.parseObject(exchange.getBody(), ViewTreesVo.class);
        return BaseResult.succeed("获取多视图设备树成功",vo);
    }

    @Override
    public BaseResult<String> selectTree(SelectTreeDto dto) {
        log.info("选择当前操作的设备树接口入参{}",dto.getDbId());
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/selecttree/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("选择当前操作的设备树中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "选择当前操作的设备树失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_SELECT_TREE_FAILED,response);
        return BaseResult.succeed("选择当前操作的设备树成功");
    }

    @Override
    public BaseResult<String> devGroups(DevGroupsDto dto) {
        log.info("获取设备组信息接口入参{}",dto.getDbId());
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/devgroups/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("获取设备组信息中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "获取设备组信息失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_DEV_GROUPS_FAILED,response);
        return BaseResult.succeed("获取设备组信息成功");
    }

    /**
     * cu非空校验以及是否登录校验
     * @param entity
     */
    private void check(CuEntity entity){
        if(ObjectUtils.isEmpty(entity)){
            throw new CuException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if(entity.getSsid()==null){
            throw new CuException(DeviceErrorEnum.DEVICE_NOT_LOGIN);
        }
    }

}
