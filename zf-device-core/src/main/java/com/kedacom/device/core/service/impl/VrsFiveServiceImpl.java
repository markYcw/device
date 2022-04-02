package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.core.basicParam.VsBasicParam;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.VrsConvert;
import com.kedacom.device.core.exception.MtException;
import com.kedacom.device.core.exception.VrsException;
import com.kedacom.device.core.mapper.VsMapper;
import com.kedacom.device.core.service.VrsFiveService;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import com.kedacom.device.vs.request.VsLoginRequest;
import com.kedacom.device.vs.response.VsLoginResponse;
import com.kedacom.device.vs.response.VsResponse;
import com.kedacom.km.api.VrsFiveApi;
import com.kedacom.svr.dto.FindByIpOrNameDto;
import com.kedacom.util.NumGen;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("vrsFiveService")
public class VrsFiveServiceImpl extends ServiceImpl<VsMapper, VsEntity> implements VrsFiveService {

    @Autowired
    private VsMapper vrsMapper;

    @Autowired
    private VrsConvert vrsConvert;

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Autowired
    private VrsFiveApi vrsFiveApi;

    @Value("${zf.svrNtyUrl.server_addr:127.0.0.1:9000}")
    private String svrNtyUrl;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/device/notify";

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String SVR_REQUEST_HEAD = "http://";

    //访问地址
    private final static String SVR_WTO = "/mid/v2/vrs";

    @Override
    public BaseResult<BasePage<VsEntity>> vrsList(VrsQuery queryDTO) {
        Page<VsEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<VsEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(queryDTO.getIp())) {
            queryWrapper.like(VsEntity::getIp, queryDTO.getIp());
        }
        if (!StringUtils.isEmpty(queryDTO.getName())) {
            queryWrapper.like(VsEntity::getName, queryDTO.getName());
        }

        Page<VsEntity> platformEntityPage = vrsMapper.selectPage(page, queryWrapper);
        List<VsEntity> records = platformEntityPage.getRecords();

        BasePage<VsEntity> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(records);
        return BaseResult.succeed(basePage);
    }

    @Override
    public BaseResult<VrsVo> saveVrs(VrsVo vrsVo) {
        log.info("============保存VRS入参VrsVo：{}", vrsVo);
        if(!isRepeat(vrsVo)){
            throw new VrsException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
        }
        VsEntity entity = vrsConvert.convertToVrsEntity(vrsVo);
        vrsMapper.insert(entity);
        vrsVo.setId(entity.getId());
        return BaseResult.succeed("新增VRS成功", vrsVo);
    }

    @Override
    public boolean isRepeat(VrsVo vrsVo) {
        VsEntity entity = vrsConvert.convertToVrsEntity(vrsVo);
        String ip = entity.getIp();
        String name = entity.getName();
        Integer id = entity.getId();
        LambdaQueryWrapper<VsEntity> wrapper = new LambdaQueryWrapper<>();
        if (id == null) {
            wrapper.eq(VsEntity::getIp, ip).or().eq(VsEntity::getName, name);
            List<VsEntity> insertList = vrsMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(insertList)) {
                log.info("===============新增VRS时IP或名称重复=================");
                return false;
            }
        } else {
            wrapper.eq(VsEntity::getIp, ip).ne(VsEntity::getId, id);
            List<VsEntity> updateList = vrsMapper.selectList(wrapper);
            LambdaQueryWrapper<VsEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(VsEntity::getName, name).ne(VsEntity::getId, id);
            List<VsEntity> terminalEntities = vrsMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(updateList) || CollectionUtil.isNotEmpty(terminalEntities)) {
                log.info("===============修改VRS2100/4100时IP或名称重复=================");
                return false;
            }
        }
        return true;
    }

    @Override
    public BaseResult delete(List<Integer> ids) {
        log.info("===========删除VRS2100/4100接口入参ids：{}", ids);
        vrsMapper.deleteBatchIds(ids);
        return BaseResult.succeed("删除VRS成功", true);
    }

    @Override
    public BaseResult<VrsVo> updateVrs(VrsVo vrsVo) {
        log.info("===========修改VRS接口入参VrsVo：{}", vrsVo);
        if (!isRepeat(vrsVo)) {
            throw new VrsException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
        }
        VsEntity entity = vrsConvert.convertToVrsEntity(vrsVo);
        vrsMapper.updateById(entity);
        return BaseResult.succeed("修改VRS信息成功", vrsVo);
    }

    @Override
    public BaseResult<VrsVo> vtsInfo(Integer id) {
        log.info("==========查询VRS接口入参id： {}", id);
        VsEntity entity = vrsMapper.selectById(id);
        if (entity == null) {
            throw new VrsException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        VrsVo vrsVo = vrsConvert.convertToVrsVo(entity);
        return BaseResult.succeed("查询VRS成功", vrsVo);
    }


    public Integer login(Integer dbId) {
        log.info("登录Vrs入参信息:{}", dbId);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        VsEntity entity = vrsMapper.selectById(dbId);
        if (entity == null) {
            throw new VrsException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        VsLoginRequest request = vrsConvert.convertToVsLogin(entity);
        String ntyUrl = REQUEST_HEAD + svrNtyUrl + NOTIFY_URL;
        request.setNtyUrl(ntyUrl);
        String url = getUrl();
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());

        log.info("登录Vrs中间件入参信息:{}", JSON.toJSONString(request));
        String string = null;
        try {
            string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        } catch (RestClientException e) {
            log.error("登录VRS失败{}", e);
        }
        log.info("登录Vrs中间件应答:{}", string);

        VsLoginResponse response = JSON.parseObject(string, VsLoginResponse.class);
        if (response.getCode() != 0) {
            log.error("登录VRS失败请查看中间件");
            return null;
        }
        entity.setSsid(response.getSsid());
        vrsMapper.updateById(entity);
        return response.getSsid();
    }

    public Integer loginByIp(String ip, String user, String passWord) {
        log.info("根据IP登录Vrs入参信息:Ip{},user:{},passWord:{}", ip, user, passWord);
        RestTemplate template = remoteRestTemplate.getRestTemplate();

        VsLoginRequest request = new VsLoginRequest();
        request.setVersion(11);
        request.setIp(ip);
        request.setUsername(user);
        request.setPassword(passWord);
        String ntyUrl = REQUEST_HEAD + svrNtyUrl + NOTIFY_URL;
        request.setNtyUrl(ntyUrl);
        String url = getUrl();
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssno", (long) NumGen.getNum());

        log.info("登录Vrs中间件入参信息:{}", JSON.toJSONString(request));
        String string = null;
        try {
            string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
        } catch (RestClientException e) {
            log.error("登录VRS失败{}", e);
        }
        log.info("登录Vrs中间件应答:{}", string);

        VsLoginResponse response = JSON.parseObject(string, VsLoginResponse.class);
        if (response.getCode() != 0) {
            log.error("登录VRS失败请查看中间件");
            return null;
        }
        return response.getSsid();
    }

    @Override
    public BaseResult<VrsRecInfoDecVo> vrsQueryHttpRec(QueryRecListVo queryRecListVo) {
        log.info("==============分页查询HTTP录像接口入参QueryRecListVo：{}", queryRecListVo);
        Integer dbId = queryRecListVo.getDbId();
        Integer login = login(dbId);
        if (login == null) {
            throw new VrsException(DeviceErrorEnum.VS_LOGIN_FAILED);
        }
        VsEntity entity = vrsMapper.selectById(dbId);
        entity.setSsid(login);
        VsBasicParam param = getParam(entity);
        log.info("分页查询HTTP录像中间件入参信息:{}", JSON.toJSONString(queryRecListVo));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/queryrec/{ssid}/{ssno}", JSON.toJSONString(queryRecListVo), String.class, param.getParamMap());
        VsResponse response = JSON.parseObject(s, VsResponse.class);
        String errorMsg = "分页查询HTTP录像失败:{},{},{}";
        handleVrs(errorMsg, DeviceErrorEnum.VS_QUERY_REC_FAILED, response);

        VrsRecInfoVo vrsRecInfoVo = JSON.parseObject(s, VrsRecInfoVo.class);
        VrsRecInfoDecVo vo = new VrsRecInfoDecVo();
        vo.setTotal(vrsRecInfoVo.getTotal());
        List<RecInfoVo> recs = vrsRecInfoVo.getRecs();
        List<RecInfoHttpVo> collect = recs.stream().map(recInfoVo -> vrsConvert.convertToRecInfoHttpVo(recInfoVo)).collect(Collectors.toList());
        vo.setRecs(collect);
        return BaseResult.succeed("分页查询HTTP录像接口成功", vo);
    }

    @Override
    public BaseResult<VrsRecInfoVo> queryRec(QueryRecVo vo) {
        log.info("==============查询录像接口入参QueryRecVo：{}", vo);
        Integer login = login(vo.getDbId());
        if (login == null) {
            throw new VrsException(DeviceErrorEnum.VS_LOGIN_FAILED);
        }
        VsEntity entity = vrsMapper.selectById(vo.getDbId());
        entity.setSsid(login);
        VsBasicParam param = getParam(entity);
        log.info("查询录像中间件入参信息:{}", JSON.toJSONString(vo));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/queryrec/{ssid}/{ssno}", JSON.toJSONString(vo), String.class, param.getParamMap());
        log.info("查询录像中间件应答:{}", s);
        VsResponse response = JSON.parseObject(s, VsResponse.class);
        String errorMsg = "查询录像失败:{},{},{}";
        handleVrs(errorMsg, DeviceErrorEnum.VS_QUERY_REC_FAILED, response);

        VrsRecInfoVo vrsRecInfoVo = JSON.parseObject(s, VrsRecInfoVo.class);
        return BaseResult.succeed("查询录像成功", vrsRecInfoVo);
    }

    @Override
    public BaseResult<LiveInfoVo> queryLive(QueryLiveVo vo) {
        log.info("==============查询直播接口入参QueryRecVo：{}", vo);
        Integer login = login(vo.getDbId());
        if (login == null) {
            throw new VrsException(DeviceErrorEnum.VS_LOGIN_FAILED);
        }
        VsEntity entity = vrsMapper.selectById(vo.getDbId());
        entity.setSsid(login);
        VsBasicParam param = getParam(entity);
        log.info("查询直播中间件入参信息:{}", JSON.toJSONString(vo));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/querylive/{ssid}/{ssno}", JSON.toJSONString(vo), String.class, param.getParamMap());
        log.info("查询直播中间件应答:{}", s);
        VsResponse response = JSON.parseObject(s, VsResponse.class);
        String errorMsg = "查询直播失败:{},{},{}";
        handleVrs(errorMsg, DeviceErrorEnum.VS_QUERY_LIVE_FAILED, response);

        LiveInfoVo liveInfoVo = JSON.parseObject(s, LiveInfoVo.class);
        return BaseResult.succeed("查询直播成功", liveInfoVo);
    }

    @Override
    public BaseResult<VrsRecInfoVo> queryRecByIp(QueryRecByIpVo vo) {
        log.info("==============根据IP查询录像接口入参QueryRecByIpVo：{}", vo);
        Integer login = loginByIp(vo.getIp(), vo.getUser(), vo.getPassWord());
        if (login == null) {
            throw new VrsException(DeviceErrorEnum.VS_LOGIN_FAILED);
        }
        VsBasicParam param = getParamBySsid(login);
        log.info("查询录像中间件入参信息:{}", JSON.toJSONString(vo));
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/queryrec/{ssid}/{ssno}", JSON.toJSONString(vo), String.class, param.getParamMap());
        log.info("查询录像中间件应答:{}", s);
        VsResponse response = JSON.parseObject(s, VsResponse.class);
        String errorMsg = "查询录像失败:{},{},{}";
        handleVrs(errorMsg, DeviceErrorEnum.VS_QUERY_REC_FAILED, response);

        VrsRecInfoVo vrsRecInfoVo = JSON.parseObject(s, VrsRecInfoVo.class);
        return BaseResult.succeed("查询录像成功", vrsRecInfoVo);
    }

    @Override
    public VsEntity getBySsid(Integer ssid) {
        log.info("=======根据ssid查找录播服务器5.1入参：{}",ssid);
        LambdaQueryWrapper<VsEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VsEntity::getSsid, ssid);
        List<VsEntity> list = vrsMapper.selectList(wrapper);
        return list.get(0);
    }

    @Override
    public void logoutSsid(Integer ssid) {
        log.info("=======登出录播服务器5.1入参：dbId：{}",ssid);
        VsEntity entity = this.getBySsid(ssid);
        VsBasicParam param = getParamBySsid(ssid);
        ResponseEntity<String> exchange = null;
        try {
            exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null,String.class, param.getParamMap());
        } catch (RestClientException e) {
            log.error("=========登出录播服务器失败，数据库ID为：{}",entity.getId());
        }
        log.info("============登出录播服务器5.1响应{}",exchange.getBody());
        entity.setSsid(null);
        vrsMapper.updateById(entity);
    }

    @Override
    public BaseResult<VrsVo> findByIpOrName(FindByIpOrNameDto dto) {
        log.info("==============根据IP或名称返回记录接口入参FindByIpOrNameDto：{}", dto);
        LambdaQueryWrapper<VsEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VsEntity::getIp,dto.getIp()).or().eq(VsEntity::getName,dto.getName());
        List<VsEntity> vsList = vrsMapper.selectList(wrapper);
        if(vsList.size()<0){
            throw new VrsException(DeviceErrorEnum.DEVICE_NOT_FOUND_ERROR);
        }else {
            VrsVo vrsVo = vrsConvert.convertToVrsVo(vsList.get(0));
            return BaseResult.succeed("查询成功",vrsVo);
        }
    }

    @Override
    public BaseResult<String> asyList() {
        List<com.kedacom.km.vo.VrsVo> list = vrsFiveApi.vrsFiveList().getData();
        Iterator<com.kedacom.km.vo.VrsVo> iterator = list.iterator();
        while (iterator.hasNext()){
            com.kedacom.km.vo.VrsVo next = iterator.next();
            VsEntity entity = new VsEntity();
            entity.setName(next.getName());
            entity.setIp(next.getIp());
            entity.setUsername(next.getUsername());
            entity.setPassword(next.getPassword());
            entity.setVersion(11);
            entity.setVrsname(next.getVrsname());
            vrsMapper.insert(entity);
        }
       return BaseResult.succeed("同步数据成功");
    }

    public String getUrl() {
        String url = SVR_REQUEST_HEAD + kmProxy + SVR_WTO;
        return url;
    }

    public VsBasicParam getParam(VsEntity entity) {
        VsBasicParam param = new VsBasicParam();
        Map<String, Long> paramMap = new HashMap<>();
        Long s = Long.valueOf(entity.getSsid());
        Long n = (long) NumGen.getNum();
        paramMap.put("ssid", s);
        paramMap.put("ssno", n);

        log.info("==========此次请求ssid为：{},ssno为：{}", s, n);
        param.setParamMap(paramMap);
        param.setUrl(getUrl());

        return param;
    }

    public VsBasicParam getParamBySsid(Integer ssid) {
        VsBasicParam param = new VsBasicParam();
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

    /**
     * 处理VRS异常
     *
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleVrs(String str, DeviceErrorEnum errorEnum, VsResponse res) {
        if (ObjectUtil.notEqual(res.getCode(), DeviceConstants.SUCCESS)) {
            log.error(str, res.getCode(), errorEnum.getCode(), errorEnum.getMsg());
            throw new MtException(res.getCode(), errorEnum.getMsg());
        }
    }


}
