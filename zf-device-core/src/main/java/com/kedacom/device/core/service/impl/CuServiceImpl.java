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
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.dto.*;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.*;
import com.kedacom.device.core.basicParam.CuBasicParam;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.CuConvert;
import com.kedacom.device.core.exception.CuException;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.cu.loadGroup.CuClient;
import com.kedacom.device.core.notify.cu.loadGroup.CuDeviceLoadThread;
import com.kedacom.device.core.notify.cu.loadGroup.CuSession;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.CuSessionStatus;
import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyHandler;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.utils.*;
import com.kedacom.device.cu.CuResponse;
import com.kedacom.device.cu.request.CuLoginRequest;
import com.kedacom.device.cu.response.CuLoginResponse;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

    @Value("${zf.cuNtyUrl.server_addr:172.16.128.105:9000}")
    private String cuNtyUrl;

    @Autowired
    private CuDeviceLoadThread cuDeviceLoadThread;

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/cu/cuNotify";
    //cu状态池 若成功登录则把数据库ID和登录状态放入此池中1为已登录，若登出则从此状态池中移除
    public static ConcurrentHashMap<Integer,Integer> cuStatusPoll = new ConcurrentHashMap<>();

    @Override
    public BaseResult<BasePage<DevEntityVo>> pageQuery(DevEntityQuery queryDTO) {
        Page<CuEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());
        //条件查询
        LambdaQueryWrapper<CuEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(queryDTO.getIp())){
            queryWrapper.like(CuEntity::getIp,queryDTO.getIp());
        }
        if(!StringUtils.isEmpty(queryDTO.getName())){
            queryWrapper.like(CuEntity::getName,queryDTO.getName());
        }

        Page<CuEntity> platformEntityPage = cuMapper.selectPage(page, queryWrapper);
        List<CuEntity> records = platformEntityPage.getRecords();
        //查询监控平台连接状态
        List<CuEntity> cuEntities = queryCuStatus(records);
        //转化为DevEntityVo
        List<DevEntityVo> vos = cuEntities.stream().map(cuEntity -> convert.convertToDevEntityVo(cuEntity)).collect(Collectors.toList());
        BasePage<DevEntityVo> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(vos);
        return BaseResult.succeed(basePage);
    }

    @Override
    public BaseResult<DevEntityVo> info(Integer kmId) {
        CuEntity cuEntity = cuMapper.selectById(kmId);
        DevEntityVo vo = convert.convertToDevEntityVo(cuEntity);
        return BaseResult.succeed("查询成功",vo);
    }

    @Override
    public BaseResult<DevEntityVo> saveDev(DevEntityVo devEntityVo) {
        synchronized (this){
            CuEntity cuEntity = convert.convertToCuEntity(devEntityVo);
            if(!isRepeat(cuEntity)){
                throw new CuException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
            }
            cuEntity.setType(DevTypeConstant.updateRecordKey);
            cuMapper.insert(cuEntity);
            DevEntityVo vo = convert.convertToDevEntityVo(cuEntity);
            return BaseResult.succeed("保存成功",vo);
        }
    }

    @Override
    public BaseResult<DevEntityVo> updateDev(DevEntityVo devEntityVo) {
        synchronized (this){
            CuEntity cuEntity = convert.convertToCuEntity(devEntityVo);
            if(!isRepeat(cuEntity)){
                throw new CuException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
            }
            cuMapper.updateById(cuEntity);
            DevEntityVo vo = convert.convertToDevEntityVo(cuEntity);
            return BaseResult.succeed("修改成功",vo);
        }
    }

    @Override
    public BaseResult<String> deleteDev(List<Integer> ids) {
        cuMapper.deleteBatchIds(ids);
        return BaseResult.succeed("删除成功");
    }

    /**
     * 对名称和IP做唯一校验
     * @return
     */
    public boolean isRepeat(CuEntity devEntity) {
        Integer id = devEntity.getId();
        String ip = devEntity.getIp();
        String name = devEntity.getName();
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        if (id == null) {
            wrapper.eq(CuEntity::getIp, ip).or().eq(CuEntity::getName,name);
            List<CuEntity> devEntitiesInsert = cuMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(devEntitiesInsert)) {
                log.info("=============添加监控平台时IP或名称重复===============");
                return false;
            }
        } else {
            wrapper.eq(CuEntity::getIp, ip).ne(CuEntity::getId,id);
            List<CuEntity> devEntitiesUpdate = cuMapper.selectList(wrapper);
            LambdaQueryWrapper<CuEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CuEntity::getName,name).ne(CuEntity::getId,id);
            List<CuEntity> devEntities = cuMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(devEntitiesUpdate)||CollectionUtil.isNotEmpty(devEntities)) {
                log.info("==================修改监控平台时IP或名称重复========================");
                return false;
            }
        }

        return true;
    }

    /**
     * 查询监控平台连接状态
     * @param records 监控平台列表
     * @return
     */
    private List<CuEntity> queryCuStatus(List<CuEntity> records){
        Iterator<CuEntity> iterator = records.iterator();
        while (iterator.hasNext()){
            CuEntity cuEntity = iterator.next();
            if(cuStatusPoll.get(cuEntity.getId())==null){
                //如果未登录则直接设置CU状态为离线
                cuEntity.setStatus(DevTypeConstant.getZero);
            }else {
                //如果已登录则给设备发送心跳，成功则设置为在线否则为离线
                try {
                    BaseResult<String> hb = this.hb(cuEntity.getId());
                    cuEntity.setStatus(DevTypeConstant.updateRecordKey);
                } catch (Exception e) {
                    log.error("==============分页查询cu，发送心跳时候发生错误{}",e);
                    //如果离线就从状态池把改cu的ID删掉并把状态设为离线
                    McuServiceImpl.mcuStatusPoll.remove(cuEntity.getId());
                    cuEntity.setStatus(DevTypeConstant.getZero);
                    continue;
                }
            }
        }
        return records;
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
    public BaseResult<DevEntityVo> loginById(CuRequestDto dto) {
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
        //设置域信息
        String domain = getDomain(entity.getId());
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(entity);
        devEntityVo.setDomainId(domain);

        //登录成功以后加载分组信息
        getGroups(dto.getDbId(),response.getSsid());
        //往cu状态池放入当前mcu状态 1已登录
        cuStatusPoll.put(dto.getDbId(), DevTypeConstant.updateRecordKey);
        return BaseResult.succeed("登录监控平台成功",devEntityVo);
    }

    /**
     * 查询平台域信息
     * @param id
     * @return
     */
    public String getDomain(Integer id){
        CuRequestDto cuRequestDto = new CuRequestDto();
        cuRequestDto.setDbId(id);
        BaseResult<LocalDomainVo> domain = this.localDomain(cuRequestDto);
        return domain.getData().getDomainId();
    }

    /**
     * 设置监控平台会话，用于保存cu底下的分组设备以及记录登录信息等
     * @param ssid
     * @param dbId 数据库ID
     */
    private void getGroups(Integer dbId,Integer ssid){
        CuClient cuClient = new CuClient();
        CuSession cuSession = new CuSession();
        cuSession.setSsid(ssid);
        cuSession.setStatus(CuSessionStatus.CONNECTED);
        cuClient.getSessionManager().putSession(cuSession);
        cuDeviceLoadThread.setCuClient(cuClient);
        //开始加载分组
        DevGroupsDto devGroupsDto = new DevGroupsDto();
        devGroupsDto.setDbId(dbId);
        devGroupsDto.setGroupId("");
        this.devGroups(devGroupsDto);
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

        //往cu状态池移除当前mcu的id
        cuStatusPoll.remove(dto.getDbId());
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
    public BaseResult<Long> time(Integer kmId) {
        log.info("获取平台时间接口入参{}",kmId);
        CuEntity entity = cuMapper.selectById(kmId);
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/time/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取平台时间中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "获取平台时间失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_TIME_FAILED,response);
        TimeVo vo = JSON.parseObject(exchange.getBody(), TimeVo.class);
        Long cuTime = Long.valueOf(vo.getTime());

        return BaseResult.succeed("获取平台时间成功",cuTime);
    }

    @Override
    public BaseResult<String> hb(Integer dbId) {
        log.info("cu发送心跳接口入参{}",dbId);
        CuEntity entity = cuMapper.selectById(dbId);
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("发送心跳中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "发送心跳失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.DEVICE_HEART_BEAT_FAILED,response);
        return BaseResult.succeed("发送心跳成功");
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
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/devicegroups/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("获取设备组信息中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "获取设备组信息失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_DEV_GROUPS_FAILED,response);
        return BaseResult.succeed("获取设备组信息成功");
    }

    @Override
    public BaseResult<String> devices(DevicesDto dto) {
        log.info("获取设备信息接口入参{}",dto.getDbId());
        CuEntity entity = cuMapper.selectById(dto.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/devices/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("获取设备信息中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "获取设备信息失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_DEVICES_FAILED,response);
        return BaseResult.succeed("获取设备信息成功");
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

    @Override
    public BaseResult<String> controlPtz(ControlPtzRequestDto requestDto) {

        log.info("监控平台id : {}, PTZ控制请求参数 : {}", requestDto.getDbId(), requestDto);
        CuEntity cuEntity = cuMapper.selectById(requestDto.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);
        ControlPtzDto controlPtzDto = convert.convertControlPtzRequestDto(requestDto);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/ptz/{ssid}/{ssno}", JSON.toJSONString(controlPtzDto), String.class, param.getParamMap());
        log.info("PTZ控制响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "PTZ控制操作失败:{},{},{}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_CONTROL_PTZ_FAILED, response);

        return BaseResult.succeed(null, "PTZ控制操作成功");
    }

}
