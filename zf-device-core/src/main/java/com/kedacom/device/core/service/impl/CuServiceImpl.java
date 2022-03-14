package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.dto.*;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.pojo.DeviceSubscribe;
import com.kedacom.cu.vo.*;
import com.kedacom.device.core.basicParam.CuBasicParam;
import com.kedacom.device.core.basicParam.CuReLoginParam;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.CuConvert;
import com.kedacom.device.core.enums.DeviceModelType;
import com.kedacom.device.core.exception.CuException;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.cu.loadGroup.CuClient;
import com.kedacom.device.core.notify.cu.loadGroup.CuDeviceLoadThread;
import com.kedacom.device.core.notify.cu.loadGroup.CuSession;
import com.kedacom.device.core.notify.cu.loadGroup.CuSessionManager;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.CuSessionStatus;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.PDevice;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.PGroup;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.SrcChn;
import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyHandler;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.utils.*;
import com.kedacom.device.cu.CuResponse;
import com.kedacom.device.cu.request.CuLoginRequest;
import com.kedacom.device.cu.response.CuLoginResponse;
import com.kedacom.exception.KMException;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private CuDeviceLoadThread cuDeviceLoadThread;

    @Resource
    private HttpServletRequest HttpServletRequest;

    @Autowired
    private LogUtil logUtil;

    private final static String modelName = "device";

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/device/notify";


    //cu状态池 若成功登录则把数据库ID和登录状态放入此池中1为已登录，若登出或者删除则从此状态池中移除
    public static ConcurrentHashMap<Integer,Integer> cuStatusPoll = new ConcurrentHashMap<>();

    //cu设备状态池 若设备加载完则把数据库ID和状态放入此池中1为已加载完所以设备，若登出则从此状态池中移除
    public static ConcurrentHashMap<Integer,Integer> cuDeviceStatusPoll = new ConcurrentHashMap<>();

    //cu心跳状态池 若登出或者掉线则从此状态池中移除
    public static ConcurrentHashMap<Integer,ScheduledThreadPoolExecutor> cuHbStatusPoll = new ConcurrentHashMap<>();

    //CU重连任务池 若是登录的时候报密码错误则从此状态池中移除
    public static ConcurrentHashMap<Integer,ScheduledThreadPoolExecutor> reTryPoll = new ConcurrentHashMap<>();

    @Override
    public BaseResult<BasePage<DevEntityVo>> pageQuery(DevEntityQuery queryDTO) {
        log.info("==========分页查询开始");
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
        log.info("分页查询监控平台链接状态开始");
        List<CuEntity> cuEntities = queryCuStatus(records);
        log.info("分页查询监控平台链接状态结束");
        //转化为DevEntityVo
        List<DevEntityVo> vos = cuEntities.stream().map(cuEntity -> convert.convertToDevEntityVo(cuEntity)).collect(Collectors.toList());
        BasePage<DevEntityVo> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(vos);
        log.info("==========分页查询结束");
        return BaseResult.succeed(basePage);
    }

    @Override
    public BaseResult<DevEntityVo> info(Integer kmId) {
        CuEntity cuEntity = cuMapper.selectById(kmId);
        DevEntityVo vo = convert.convertToDevEntityVo(cuEntity);
        String domainId = "";
        if(cuStatusPoll.get(vo.getId())!=null){
           //先判断有没有域id在数据库记录如果没有则查一遍然后更新入库
            if(cuEntity.getModelType()==null||cuEntity.getModelType().equals("")){
                domainId = this.getDomainSingle(vo.getId());
                cuEntity.setModelType(domainId);
                cuMapper.updateById(cuEntity);
                vo.setDomainId(domainId);
            }
            vo.setStatus(1);
        }else {
            vo.setStatus(0);
        }
        return BaseResult.succeed("查询成功",vo);
    }

    @Override
    public BaseResult<DevEntityVo> saveDev(DevEntityVo devEntityVo) {
        synchronized (this){
            CuEntity cuEntity = convert.convertToCuEntity(devEntityVo);
            if(!isRepeat(cuEntity)){
                throw new CuException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
            }
            cuEntity.setType(DeviceModelType.CU2.getCode());
            cuMapper.insert(cuEntity);
            DevEntityVo vo = convert.convertToDevEntityVo(cuEntity);
            //保存成功后登录平台
            CuRequestDto dto = new CuRequestDto();
            dto.setKmId(cuEntity.getId());
            loginById(dto);
            return BaseResult.succeed("保存成功",vo);
        }
    }

    @Override
    public BaseResult<DevEntityVo> updateDev(DevEntityVo devEntityVo) {
        log.info("修改CU接口入参：{}",devEntityVo);
        synchronized (this){
            CuEntity cuEntity = convert.convertToCuEntity(devEntityVo);
            if(!isRepeat(cuEntity)){
                throw new CuException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
            }
            CuRequestDto dto = new CuRequestDto();
            dto.setKmId(cuEntity.getId());
            try {
                this.logoutById(dto);
            } catch (Exception e) {
                log.info("======更新时登出CU失败",e);
            }
            cuMapper.updateById(cuEntity);
            try {
                this.loginById(dto);
            } catch (Exception e) {
                log.info("======更新时登录CU失败",e);
            }

            DevEntityVo vo = convert.convertToDevEntityVo(cuEntity);
            return BaseResult.succeed("修改成功",vo);
        }
    }

    @Override
    public BaseResult<String> deleteDev(List<Integer> ids) {
        log.info("=======删除CU接口入参：{}",ids);
        for (Integer id : ids) {
            if(cuStatusPoll.get(id)!=null){
                CuRequestDto dto = new CuRequestDto();
                dto.setKmId(id);
                try {
                    logoutById(dto);
                } catch (Exception e) {
                    log.error("======删除CU时登出失败{}",e);
                }
            }
            cuMapper.deleteById(id);
        }
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
                    cuStatusPoll.remove(cuEntity.getId());
                    //如果离线就从设备状态池把改cu的ID删掉
                    cuDeviceStatusPoll.remove(cuEntity.getId());
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

        /*if(ssid !=null){
            CompletableFuture.runAsync(()->NotifyHandler.getInstance().distributeMessages(ssid,DeviceType.CU2.getValue(),type,notify));
        }*/
        NotifyHandler.getInstance().distributeMessages(ssid,DeviceType.CU2.getValue(),type,notify);

    }

    @Override
    public BaseResult<DevEntityVo> loginById(CuRequestDto dto) {
        synchronized (this){
            log.info("登录cu入参信息:{}", dto.getKmId());
            //登录之前先判断改平台是否已经登录，如果已经登录告知业务该设备已登录请勿重复登录
            if(cuStatusPoll.get(dto.getKmId())!=null){
                return BaseResult.succeed("该平台已登录请勿重复登录");
            }
            RestTemplate template = remoteRestTemplate.getRestTemplate();
            CuEntity entity = cuMapper.selectById(dto.getKmId());
            if(entity == null){
                throw new CuException(DeviceErrorEnum.DEVICE_NOT_FOUND);
            }
            CuLoginRequest request = convert.convertToCuLoginRequest(entity);
            String ntyUrl = REQUEST_HEAD + cuNtyUrl + NOTIFY_URL;
            request.setNtyUrl(ntyUrl);
            request.setDevType(DeviceModelType.CU2.getCode());
            String url = factory.geturl(entity.getType());
            Map<String, Long> paramMap = new HashMap<>();
            paramMap.put("ssno", (long) NumGen.getNum());

            log.info("登录cu中间件入参信息:{},登录cu的ssno为：{}", JSON.toJSONString(request),paramMap);
            String string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
            log.info("登录cu中间件应答:{}", string);

            CuLoginResponse response = JSON.parseObject(string, CuLoginResponse.class);
            //如果是密码错误或者是用户不存在首先去除定时任务不进行无限重连
            if(response.getCode()!=0){
                if(response.getCode()==10012||response.getCode()==10011||response.getCode()==9||response.getCode()==10){
                    removeReTryLogin(entity.getId());
                    return BaseResult.failed("登录失败，用户名或密码错误请检查");
                }else {
                    return BaseResult.failed("登录失败，请稍后重试");
                }
            }
            //如果登录成功再把ssid保存进数据库
            entity.setSsid(response.getSsid());
            entity.setModifyTime(new Date());
            cuMapper.updateById(entity);
            DevEntityVo devEntityVo = convert.convertToDevEntityVo(entity);
            devEntityVo.setStatus(DevTypeConstant.updateRecordKey);

            //登录成功以后加载分组信息
            CompletableFuture.runAsync(()-> getGroups(dto.getKmId(),response.getSsid()));
            //往cu状态池放入当前mcu状态 1已登录
            cuStatusPoll.put(dto.getKmId(), DevTypeConstant.updateRecordKey);
            //发送心跳
            hbTask(entity.getId());
            return BaseResult.succeed("登录监控平台成功",devEntityVo);
        }
    }

    /**
     * 去除定时任务不进行无限重连
     * @param dbId 数据库ID
     */
    private void removeReTryLogin(Integer dbId){
        ScheduledThreadPoolExecutor schedule = reTryPoll.get(dbId);
        if(ObjectUtil.isNotNull(schedule)){
            schedule.shutdownNow();
            reTryPoll.remove(dbId);
        }
    }

    /**
     * 查询平台域信息
     * @param vos
     * @return
     */
    public List<DevEntityVo> getDomain(List<DevEntityVo> vos){
        Iterator<DevEntityVo> iterator = vos.iterator();
        while (iterator.hasNext()){
            DevEntityVo vo = iterator.next();
            if(vo.getStatus()==1){
                CuRequestDto cuRequestDto = new CuRequestDto();
                cuRequestDto.setKmId(vo.getId());
                BaseResult<LocalDomainVo> domain = this.localDomain(cuRequestDto);
                vo.setDomainId(domain.getData().getDomainId());
            }
        }
        return vos;
    }

    /**
     * 设置单个监控平台域信息
     * @param id
     * @return
     */
    public String getDomainSingle(Integer id){
        CuRequestDto cuRequestDto = new CuRequestDto();
        cuRequestDto.setKmId(id);
        BaseResult<LocalDomainVo> domain = this.localDomain(cuRequestDto);
        return domain.getData().getDomainId();
        }

    /**
     * 设置监控平台会话，用于保存cu底下的分组设备以及记录登录信息等
     * @param ssid
     * @param dbId 数据库ID
     */
    private void getGroups(Integer dbId,Integer ssid){
        //把CuSession加载到设备容器cuDeviceLoadThread
        CuSession cuSession = new CuSession();
        cuSession.setSsid(ssid);
        cuSession.setStatus(CuSessionStatus.CONNECTED);
        cuDeviceLoadThread.getCuClient().getSessionManager().putSession(cuSession);
        //开始加载分组
        DevGroupsDto devGroupsDto = new DevGroupsDto();
        devGroupsDto.setKmId(dbId);
        devGroupsDto.setGroupId("");
        this.devGroups(devGroupsDto);
    }

    @Override
    public BaseResult logoutById(CuRequestDto dto) {
        synchronized (this){
            CuEntity entity = cuMapper.selectById(dto.getKmId());
            check(entity);
            //去除底层session
            CuSessionManager manager = cuDeviceLoadThread.getCuClient().getSessionManager();
            manager.removeSession(entity.getSsid());
            //去除心跳定时任务
            ScheduledThreadPoolExecutor Executor = cuHbStatusPoll.get(entity.getId());
            if(ObjectUtil.isNotNull(Executor)){
                Executor.shutdownNow();
                cuHbStatusPoll.remove(entity.getId());
            }
            //往cu状态池移除当前cu的id
            cuStatusPoll.remove(dto.getKmId());
            //从cu设备状态池中去除当前cu的ID
            cuDeviceStatusPoll.remove(dto.getKmId());
            CuBasicParam param = tool.getParam(entity);
            log.info("根据ID登出cu接口入参kmId：{},ssid/ssno{}",dto.getKmId(),param);
            ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
            CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
            String errorMsg = "登出cu失败:{},{},{}";
            responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_LOGOUT_FAILED, response);
            LambdaUpdateWrapper<CuEntity> wrapper = new LambdaUpdateWrapper();
            wrapper.set(CuEntity::getSsid,null)
                    .set(CuEntity::getModifyTime,new Date())
                    .eq(CuEntity::getId,dto.getKmId());
            cuMapper.update(null,wrapper);
            return BaseResult.succeed("登出cu成功");
        }

    }

    @Override
    public BaseResult<LocalDomainVo> localDomain(CuRequestDto dto) {
        log.info("获取平台域信息接口入参{}",dto);
        CuEntity entity = cuMapper.selectById(dto.getKmId());
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
        log.info("获取域链表接口入参{}",dto.getKmId());
        CuEntity entity = cuMapper.selectById(dto.getKmId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/domains/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取域链表中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "获取域链表失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_DOMAINS_FAILED,response);
        DomainsVo vo = JSON.parseObject(exchange.getBody(), DomainsVo.class);
        //记录操作日志
        logUtil.operateLog(modelName,"获取域链表成功",HttpServletRequest.getHeader("Authorization"));
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
        //记录操作日志
        logUtil.operateLog(modelName,"获取平台时间成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("获取平台时间成功",cuTime);
    }

    /**
     * 发送心跳 发送失败以后重试3次 三次都失败则不再发送
     * @param dbId
     * @return
     */
    @Override
    public BaseResult<String> hb(Integer dbId) {
        log.info("cu发送心跳接口入参{}",dbId);
        CuEntity entity = cuMapper.selectById(dbId);
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        log.info("发送心跳中间件入参ssno/ssid{}",param.getParamMap());
        ResponseEntity<String> exchange = null;
        try {
            exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
            log.info("发送心跳中间件响应{}",exchange.getBody());
            CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
            if (response.getCode()!=0){
                CuReLoginParam p = new CuReLoginParam();
                p.setDbId(dbId);
                this.reTryLogin(p);
                return BaseResult.failed("发送心跳失败");
            }
        } catch (Exception e) {
          log.error("===============发送心跳时发生异常，即将进行自动重连，数据库ID为：{},错误原因为{}",dbId,e);
            //中间件挂了以后先去除心跳任务
            removeHbTask(dbId);
            //进行重连
            CuReLoginParam paramLogin = new CuReLoginParam();
            paramLogin.setDbId(dbId);
            CompletableFuture.runAsync(()->ContextUtils.publishReLogin(paramLogin));
            return BaseResult.failed("发送心跳失败");
        }

        return BaseResult.succeed("发送心跳成功");
    }

    /**
     * 去除心跳任务并且状态置为离线
     * @param dbId
     */
    public void removeHbTask(Integer dbId){
        log.info("==========去除心跳任务");
        ScheduledThreadPoolExecutor scheduled = cuHbStatusPoll.get(dbId);
        if(ObjectUtil.isNotNull(scheduled)){
            scheduled.shutdownNow();
        }
        if(cuStatusPoll.get(dbId)!=null){
            cuStatusPoll.remove(dbId);
        }
    }

    /**
     * 去除会话和ssId
     * @param dbId
     */
    public void removeSession(Integer dbId){
        log.info("==============去除CUSession数据库ID为：{}",dbId);
        CuEntity entity = cuMapper.selectById(dbId);
        CuClient cuClient = cuDeviceLoadThread.getCuClient();
        cuClient.getSessionManager().removeSession(entity.getSsid());
        entity.setSsid(null);
        cuMapper.updateById(entity);
    }

    /**
     * 根据数据库ID自动重连每一分钟重连一次
     * @param paramLogin
     */
    @EventListener
    public void reTryLogin(CuReLoginParam paramLogin){
        log.info("==================观察者收到CU发送心跳失败，即将进行自动重连，数据库ID为：{}",paramLogin.getDbId());
        CuRequestDto dto = new CuRequestDto();
        dto.setKmId(paramLogin.getDbId());
        try {
            this.logoutById(dto);
        } catch (Exception e) {
            log.error("===============发送心跳时失败尝试重启监控平台的时候先做登出，但登出失败");
        }
        reTryLoginNow(paramLogin.getDbId());
    }

    /**
     * 根据数据库ID自动重连每一分钟重连一次
     * @param dbId
     */
    @Override
    public void reTryLoginNow(Integer dbId){
        log.info("=====================CU即将进行自动重连数据库ID为：{}",dbId);
        CuRequestDto dto = new CuRequestDto();
        dto.setKmId(dbId);
        //首先登出
        try {
            logoutById(dto);
        } catch (Exception e) {
            log.error("=============中间件重启/或者cu掉线后登出失败{}",e);
        }
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2,
                new ThreadFactoryBuilder().setNameFormat("reTryLogin-pool-%d").build());
        reTryPoll.put(dbId,scheduled);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                BaseResult<DevEntityVo> baseResult = null;
                try {
                    baseResult = loginById(dto);
                    if (baseResult.getErrCode() == 0) {
                        scheduled.shutdownNow();
                    }
                } catch (Exception e) {
                    log.error("=======定时任务重连监控平台失败");
                }

            }
        }, 60, 60, TimeUnit.SECONDS);

    }

        @Override
    public void initCu() {
        log.info("======服务启动登录平台");
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNotNull(cuEntity -> cuEntity.getId());
        List<CuEntity> list = cuMapper.selectList(null);
        Iterator<CuEntity> iterator = list.iterator();
        while (iterator.hasNext()) {
            CuEntity next = iterator.next();
            CuRequestDto dto = new CuRequestDto();
            dto.setKmId(next.getId());
            try {
                this.loginById(dto);
            } catch (Exception e) {
                log.error("服务启动登录CU失败{}", e);
            }
        }
    }

    @Override
    public void logoutCu() {
        log.info("======服务启动登出平台");
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNotNull(cuEntity -> cuEntity.getId());
        List<CuEntity> list = cuMapper.selectList(null);
        Iterator<CuEntity> iterator = list.iterator();
        while (iterator.hasNext()) {
            CuEntity next = iterator.next();
            CuRequestDto dto = new CuRequestDto();
            dto.setKmId(next.getId());
            try {
                this.logoutById(dto);
            } catch (Exception e) {
                log.error("服务启动登出CU失败{}", e);
            }
        }
    }

    @Override
    public void subscribe(DeviceSubscribe deviceSubscribe) {
        log.info("单个设备状态订阅接口入参{}",deviceSubscribe);
        CuEntity entity = cuMapper.selectById(deviceSubscribe.getDbId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        log.info("单个设备状态订阅接口入参/ssid/ssno",param.getParamMap());
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/subscribe/{ssid}/{ssno}", JSON.toJSONString(deviceSubscribe), String.class, param.getParamMap());
        log.info("单个设备状态订阅中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "单个设备状态订阅失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_SUBSCRIBE_ERROR,response);
    }

    @Override
    public CuEntity getBySsid(Integer ssid) {
       log.info("=======根据ssid获取cu入参ssid{}",ssid);
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid,ssid);
        List<CuEntity> cuEntities = cuMapper.selectList(wrapper);
        return cuEntities.get(DevTypeConstant.getZero);
    }


    /**
     * cu维护心跳定时任务 每1分钟发一次心跳
     * @param dbId
     */
    public void hbTask(Integer dbId){
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2,
                new ThreadFactoryBuilder().setNameFormat("hbTask-pool-%d").build());
        // 创建定时任务，每1分钟发送一次心跳
        scheduled.scheduleAtFixedRate(()->{
            hb(dbId);
        },1,60,  TimeUnit.SECONDS);//延迟1秒每1分钟发一次心跳
        //往心跳状态池放入当前执行任务的scheduled
        cuHbStatusPoll.put(dbId,scheduled);
    }

    @Override
    public BaseResult<ViewTreesVo> viewTrees(CuRequestDto dto) {
        log.info("获取多视图设备树接口入参{}",dto.getKmId());
        CuEntity entity = cuMapper.selectById(dto.getKmId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/viewtrees/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取多视图设备树中间件响应{}",exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "获取多视图设备树失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_VIEW_TREES_FAILED,response);
        ViewTreesVo vo = JSON.parseObject(exchange.getBody(), ViewTreesVo.class);
        //记录操作日志
        logUtil.operateLog(modelName,"获取多视图设备树成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("获取多视图设备树成功",vo);
    }

    @Override
    public BaseResult<String> selectTree(SelectTreeDto dto) {
        log.info("选择当前操作的设备树接口入参{}",dto.getKmId());
        CuEntity entity = cuMapper.selectById(dto.getKmId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/selecttree/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("选择当前操作的设备树中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "选择当前操作的设备树失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_SELECT_TREE_FAILED,response);
        //记录操作日志
        logUtil.operateLog(modelName,"选择当前操作的设备树成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("选择当前操作的设备树成功");
    }

    @Override
    public BaseResult<String> devGroups(DevGroupsDto dto) {
        log.info("获取设备组信息接口入参{}",dto.getKmId());
        CuEntity entity = cuMapper.selectById(dto.getKmId());
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
        log.info("获取设备信息接口入参{}",dto);
        CuEntity entity = cuMapper.selectById(dto.getKmId());
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
        if(cuStatusPoll.get(entity.getId())==null){
            throw new CuException(DeviceErrorEnum.DEVICE_NOT_LOGIN);
        }
    }

    @Override
    public BaseResult<String> controlPtz(ControlPtzRequestDto requestDto) {

        log.info("监控平台id : {}, PTZ控制请求参数 : {}", requestDto.getKmId(), requestDto);
        CuEntity cuEntity = cuMapper.selectById(requestDto.getKmId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);
        ControlPtzDto controlPtzDto = convert.convertControlPtzRequestDto(requestDto);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/ptz/{ssid}/{ssno}", JSON.toJSONString(controlPtzDto), String.class, param.getParamMap());
        log.info("PTZ控制响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "PTZ控制操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_CONTROL_PTZ_FAILED, response);
        //记录操作日志
        logUtil.operateLog(modelName,"PTZ控制操作成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, "PTZ控制操作成功");
    }

    @Override
    public BaseResult<Boolean> startRec(PlatRecStartVo platRecStartVo) {

        CuEntity cuEntity = cuMapper.selectById(platRecStartVo.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);
        PlayRecDto playRecDto = convert.convertPlayRecDto(platRecStartVo);
        playRecDto.setType(0);
        log.info("开启平台录像请求参数信息 : {}", playRecDto);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/platrec/{ssid}/{ssno}", JSON.toJSONString(playRecDto), String.class, param.getParamMap());
        log.info("开启平台录像响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "开启平台录像操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_START_REC_FAILED, response);
        //记录操作日志
        logUtil.operateLog(modelName,"开启平台录像成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, true);
    }

    @Override
    public BaseResult<Boolean> stopRec(PlatRecStopVo platRecStopVo) {

        CuEntity cuEntity = cuMapper.selectById(platRecStopVo.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);
        PlayRecDto playRecDto = convert.convertPlayRecDto(platRecStopVo);
        playRecDto.setType(1);
        log.info("关闭平台录像请求参数信息 : {}", playRecDto);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/platrec/{ssid}/{ssno}", JSON.toJSONString(playRecDto), String.class, param.getParamMap());
        log.info("关闭平台录像响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "关闭平台录像操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_STOP_REC_FAILED, response);
        //记录操作日志
        logUtil.operateLog(modelName,"关闭平台录像成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, true);
    }

    @Override
    public BaseResult<Boolean> startPuRec(PuRecStartVo puRecStartVo) {

        CuEntity cuEntity = cuMapper.selectById(puRecStartVo.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);
        PlayRecDto playRecDto = convert.convertPlayRecDto(puRecStartVo);
        playRecDto.setType(0);
        log.info("开启前端录像请求参数信息 : {}", playRecDto);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/purec/{ssid}/{ssno}", JSON.toJSONString(playRecDto), String.class, param.getParamMap());
        log.info("开启前端录像响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "开启前端录像操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_START_PU_REC_FAILED, response);
        //记录操作日志
        logUtil.operateLog(modelName,"开启前端录像成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, true);
    }

    @Override
    public BaseResult<Boolean> stopPuRec(PuRecStopVo puRecStopVo) {

        CuEntity cuEntity = cuMapper.selectById(puRecStopVo.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);
        PlayRecDto playRecDto = convert.convertPlayRecDto(puRecStopVo);
        playRecDto.setType(1);
        log.info("关闭前端录像请求参数信息 : {}", playRecDto);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/purec/{ssid}/{ssno}", JSON.toJSONString(playRecDto), String.class, param.getParamMap());
        log.info("关闭前端录像响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "关闭前端录像操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_STOP_PU_REC_FAILED, response);
        //记录操作日志
        logUtil.operateLog(modelName,"关闭前端录像成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, true);
    }

    @Override
    public BaseResult<Boolean> openLockingRec(OpenLockingRecRequestDto requestDto) {

        Date endTime = requestDto.getEndTime();
        Date startTime = requestDto.getStartTime();

        OperateLockingRecDto operateLockingRecDto = convert.convertOperateLockDto(requestDto);
        operateLockingRecDto.setType(0);
        operateLockingRecDto.setEndTime(DateUtils.getDateString(endTime));
        operateLockingRecDto.setStartTime((DateUtils.getDateString(startTime)));
        log.info("打开录像锁定请求参数信息 : {}", operateLockingRecDto.toString());
        CuEntity cuEntity = cuMapper.selectById(requestDto.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/lockrec/{ssid}/{ssno}", JSON.toJSONString(operateLockingRecDto), String.class, param.getParamMap());
        log.info("打开录像锁定响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "打开录像锁定操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_OPEN_LOCKING_REC_FAILED, response);
        //记录操作日志
        logUtil.operateLog(modelName,"打开录像锁定成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, true);
    }

    @Override
    public BaseResult<Boolean> cancelLockingRec(CancelLockingRecRequestDto requestDto) {

        Date endTime = requestDto.getEndTime();
        Date startTime = requestDto.getStartTime();

        OperateLockingRecDto operateLockingRecDto = convert.convertOperateLockDto(requestDto);
        operateLockingRecDto.setType(1);
        operateLockingRecDto.setEndTime(DateUtils.getDateString(endTime));
        operateLockingRecDto.setStartTime((DateUtils.getDateString(startTime)));
        log.info("取消录像锁定请求参数信息 : {}", operateLockingRecDto.toString());
        CuEntity cuEntity = cuMapper.selectById(requestDto.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/lockrec/{ssid}/{ssno}", JSON.toJSONString(operateLockingRecDto), String.class, param.getParamMap());
        log.info("取消录像锁定响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "取消录像锁定操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_CANCEL_LOCKING_REC_FAILED, response);
        //记录操作日志
        logUtil.operateLog(modelName,"取消录像锁定成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, true);
    }

    @Override
    public BaseResult<QueryVideoResponseVo> queryVideo(QueryVideoRequestDto requestDto) {

        Date endTime = requestDto.getEndTime();
        Date startTime = requestDto.getStartTime();

        QueryVideoDto queryVideoDto = convert.convertQueryVideoDto(requestDto);
        queryVideoDto.setEndTime(DateUtils.getDateString(endTime));
        queryVideoDto.setStartTime(DateUtils.getDateString(startTime));
        log.info("查询录像请求参数 ： {}, 平台id ： {}", queryVideoDto.toString(), requestDto.getDbId());
        CuEntity cuEntity = cuMapper.selectById(requestDto.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/queryrec/{ssid}/{ssno}", JSON.toJSONString(queryVideoDto), String.class, param.getParamMap());
        log.info("查询录像响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "查询录像操作失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_QUERY_VIDEO_FAILED, response);
        QueryVideoResponseVo responseVo = JSONObject.parseObject(responseStr, QueryVideoResponseVo.class);
        //记录操作日志
        logUtil.operateLog(modelName,"查询录像成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, responseVo);
    }

    @Override
    public BaseResult<QueryVideoCalendarResponseVo> queryVideoCalendar(QueryVideoCalendarRequestDto requestDto) {

        Date endTime = requestDto.getEndTime();
        Date startTime = requestDto.getStartTime();

        QueryVideoCalendarDto queryVideoCalendarDto = convert.convertQueryVideoCalendarDto(requestDto);
        queryVideoCalendarDto.setEndTime(DateUtils.getDateString(endTime));
        queryVideoCalendarDto.setStartTime(DateUtils.getDateString(startTime));
        log.info("查询录像日历请求参数类 ： {}, 平台id ： {}", queryVideoCalendarDto.toString(), requestDto.getDbId());
        CuEntity cuEntity = cuMapper.selectById(requestDto.getDbId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);

        String responseStr = remoteRestTemplate.getRestTemplate()
                .postForObject(param.getUrl() + "/queryrecdays/{ssid}/{ssno}", JSON.toJSONString(queryVideoCalendarDto), String.class, param.getParamMap());
        log.info("查询录像日历响应参数 : {}", responseStr);
        CuResponse response = JSONObject.parseObject(responseStr, CuResponse.class);
        String errorMsg = "查询录像日历失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_QUERY_VIDEO_DAYS_FAILED, response);
        QueryVideoCalendarResponseVo responseVo = JSON.parseObject(responseStr, QueryVideoCalendarResponseVo.class);
        //记录操作日志
        logUtil.operateLog(modelName,"查询录像日历成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, responseVo);
    }

    @Override
    public BaseResult<QueryDiskResponseVo> queryDisk(QueryDiskRequestDto requestDto) {

        log.info("查询磁阵(磁盘)信息请求参数，平台id : {}", requestDto.getKmId());
        CuEntity cuEntity = cuMapper.selectById(requestDto.getKmId());
        check(cuEntity);
        CuBasicParam param = tool.getParam(cuEntity);

        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate()
                .exchange(param.getUrl() + "/disks/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("查询磁阵(磁盘)信息响应参数 : {}", exchange.getBody());
        CuResponse response = JSONObject.parseObject(exchange.getBody(), CuResponse.class);
        String errorMsg = "查询磁阵(磁盘)信息失败 : {}, {}, {}";
        assert response != null;
        responseUtil.handleCuRes(errorMsg, DeviceErrorEnum.CU_QUERY_DISK_FAILED, response);
        QueryDiskResponseVo responseVo = JSON.parseObject(exchange.getBody(), QueryDiskResponseVo.class);
        //记录操作日志
        logUtil.operateLog(modelName,"查询磁阵(磁盘)信息成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed(null, responseVo);
    }

    /**
     * 根据条件返回监控平台树
     * @param vo
     * @return
     */
    @Override
    public BaseResult<DevEntityVo> findByCondition(FindCuByConditionVo vo) {
        log.info("=============根据条件返回监控平台树接口入参FindCuByConditionVo{}",vo);
        Integer kmId = vo.getKmId();
        CuEntity devEntity = cuMapper.selectById(kmId);
        check(devEntity);
        //设备名称
        String name = vo.getName();
        //设备在线状态
        Integer pageStatus = vo.getStatus();
        //设备类型
        Integer deviceType = vo.getDeviceType();
        if(ObjectUtils.isEmpty(devEntity)){
            throw new CuException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
            try {

                    if (name == null && pageStatus == 1 && deviceType == null) {   //查询所有在线设备

                        devEntityVo = this.getDevEntityVoFilter(devEntity.getId(), pageStatus);
                    } else if (name == null && pageStatus == 2 && deviceType == null) {    //查询所有离线设备

                        devEntityVo = this.getDevEntityVoFilter(devEntity.getId(), pageStatus);
                    }else if((name == null|| name.equals("")) && pageStatus != null && deviceType != null){   //根据设备类型和在线状态查询

                        devEntityVo = this.getDevEntityVoByDeviceTypeAndStatus(devEntity.getId(), deviceType, pageStatus);
                        devEntityVo = this.removeEmptySortGroup(devEntityVo);
                    } else if (name != null && pageStatus == 0 && deviceType == null) {   //根据设备名称查询

                        devEntityVo = this.getDevEntityVoByName(devEntity.getId(), name);
                        devEntityVo = this.removeEmptySortGroup(devEntityVo);
                    } else if (name != null && (pageStatus == 1 || pageStatus == 2) && deviceType == null) {   //根据设备名称和设备在线状态查询

                        devEntityVo = this.getDevEntityVoByNameAndStatus(devEntity.getId(), name, pageStatus);
                        devEntityVo = this.removeEmptySortGroup(devEntityVo);
                    } else if(name != null && pageStatus == 0 && deviceType != null ){   //根据设备名称和设备类型查询

                        devEntityVo = this.getDevEntityVoByDeviceTypeAndName(devEntity.getId(), deviceType, name);
                        devEntityVo = this.removeEmptySortGroup(devEntityVo);
                    }else {

                        devEntityVo = this.getDevEntityVo(devEntity.getId()); //查询所有设备
                    }
              devEntityVo.setStatus(DevTypeConstant.updateRecordKey);
            } catch (Exception e) {
                log.error("获取监控平台状态失败：{}", e.getMessage());
                devEntityVo.setStatus(DevTypeConstant.updateRecordKey);
            }

        return BaseResult.succeed("查询成功",devEntityVo);
    }

    @Override
    public BaseResult<DevEntityVo> queryMonitor(Integer kmId) {
        log.info("=============根据数据库ID返回监控平台树接口入参kmId{}",kmId);
        DevEntityVo devEntityVo = null;
        CuEntity devEntity = cuMapper.selectById(kmId);
        check(devEntity);

        try {
            devEntityVo = getDevEntityVo(kmId);
            log.info("===============queryMonitor查询监控平台树结果为：{}",devEntityVo);
        } catch (KMException e) {
            log.error("查询监控平台树状图信息失败请稍后重试{}", e.getMessage());
            throw new CuException(DeviceErrorEnum.QUERY_MONITOR_ERROR);
        }
        return BaseResult.succeed( "查询监控平台树状信息成功",devEntityVo);
    }

    @Override
    public BaseResult<CuDeviceVo> getCuDeviceInfo(Integer kmId, String puId) {
        log.info("=============获取设备详细信息接口入参kmId{}",kmId);
        CuEntity devEntity = cuMapper.selectById(kmId);
        check(devEntity);
        Integer ssid = devEntity.getSsid();
        try {
            CuSession cuSession = cuDeviceLoadThread.getCuClient().getSessionManager().getSessionBySSID(ssid);
            PDevice device = cuSession.getDeviceCache().getDevice(puId);
            if(ObjectUtils.isEmpty(device)){
                log.error("根据puId查询不到设备，请检查参数是否正确");
                throw new CuException(DeviceErrorEnum.GET_CU_DEVICE_INFO_ERROR);
            }
            CuDeviceVo cuDeviceVo = convert.covertToCuDeviceVo(device);
            //记录操作日志
            logUtil.operateLog(modelName,"获取设备详细信息成功",HttpServletRequest.getHeader("Authorization"));
            return BaseResult.succeed( "获取设备详细信息成功",cuDeviceVo);
        } catch (Exception e) {
            log.error("获取设备详细信息失败请稍后重试{}", e.getMessage());
            throw new CuException(DeviceErrorEnum.GET_CU_DEVICE_INFO_ERROR);
        }
    }

    @Override
    public BaseResult<CuChannelVo> getCuChannelInfo(Integer kmId, String puId, Integer sn) {
        log.info("=============获取设备具体通道信息接口入参kmId{}",kmId);
        CuEntity devEntity = cuMapper.selectById(kmId);
        check(devEntity);
        Integer ssid = devEntity.getSsid();
        try {
            CuSession cuSession = cuDeviceLoadThread.getCuClient().getSessionManager().getSessionBySSID(ssid);
            PDevice device = cuSession.getDeviceCache().getDevice(puId);
            if(ObjectUtils.isEmpty(device)){
                log.error("根据puId查询不到设备，请检查参数是否正确");
                throw new CuException(DeviceErrorEnum.GET_CU_CHANNEL_INFO_ERROR);
            }
            List<SrcChn> channels = device.getChannels();
            if (CollectionUtils.isEmpty(channels)) {
                return null;
            }
            Optional<SrcChn> optionalCuChannel = channels.stream().filter(x -> sn == x.getSn()).findFirst();
            CuChannelVo cuChannelVo = null;
            if(optionalCuChannel.isPresent()){
                SrcChn pChannel = optionalCuChannel.get();
                cuChannelVo = convert.convertToCuChannelVo(pChannel);
            }
            //记录操作日志
            logUtil.operateLog(modelName,"获取设备具体通道信息成功",HttpServletRequest.getHeader("Authorization"));
            return BaseResult.succeed("获取设备具体通道信息成功",cuChannelVo);
        } catch (Exception e) {
            log.error("获取设备通道具体通道信息失败{}", e.getMessage());
            throw new CuException(DeviceErrorEnum.GET_CU_CHANNEL_INFO_ERROR);
        }
    }

    @Override
    public BaseResult<List<CuChannelVo>> getCuChannelList(CuChnListDto requestDto) {
        log.info("=============获取设备通道集合接口入参CuChnListDto{}",requestDto);
        CuEntity devEntity = cuMapper.selectById(requestDto.getKmId());
        check(devEntity);
        Integer ssid = devEntity.getSsid();
        try {
            CuSession cuSession = cuDeviceLoadThread.getCuClient().getSessionManager().getSessionBySSID(ssid);
            PDevice device = cuSession.getDeviceCache().getDevice(requestDto.getPuId());
            if(ObjectUtils.isEmpty(device)){
                log.error("根据puId查询不到设备，请检查参数是否正确");
                throw new CuException(DeviceErrorEnum.GET_CU_CHANNEL_INFO_ERROR);
            }
            List<SrcChn> channels = device.getChannels();
            List<CuChannelVo> collect = channels.stream().map(a -> convert.convertToCuChannelVo(a)).collect(Collectors.toList());
            //记录操作日志
            logUtil.operateLog(modelName,"获取设备通道集合成功",HttpServletRequest.getHeader("Authorization"));
            return BaseResult.succeed("获取设备通道集合成功",collect);
        } catch (Exception e) {
            log.error("获取获取设备通道集合失败{}", e.getMessage());
            throw new CuException(DeviceErrorEnum.GET_CU_CHANNEL_LIST_ERROR);
        }
    }

    @Override
    public BaseResult<GbIdVo> gbId(GbIdDto requestDto) {
        log.info("=============获取国标id接口入参GbIdDto{}",requestDto);
        CuEntity entity = cuMapper.selectById(requestDto.getKmId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/gbid/{ssid}/{ssno}", JSON.toJSONString(requestDto), String.class, param.getParamMap());
        log.info("获取国标id接口中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "获取国标id接口失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_GB_ID_ERROR,response);
        GbIdVo vo = JSON.parseObject(s, GbIdVo.class);
        //记录操作日志
        logUtil.operateLog(modelName,"获取国标id接口成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("获取国标id接口成功",vo);
    }

    @Override
    public BaseResult<PuIdTwoVo> puIdTwo(PuIdTwoDto requestDto) {
        log.info("=============获取平台2.0puId接口入参PuIdTwoDto{}",requestDto);
        CuEntity entity = cuMapper.selectById(requestDto.getKmId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/puid20/{ssid}/{ssno}", JSON.toJSONString(requestDto), String.class, param.getParamMap());
        log.info("获取平台2.0puId接口中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "获取平台2.0puId失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_Pu_ID_TWO_ERROR,response);
        PuIdTwoVo vo = JSON.parseObject(s, PuIdTwoVo.class);
        //记录操作日志
        logUtil.operateLog(modelName,"获取平台2.0puId成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("获取平台2.0puId成功",vo);
    }

    @Override
    public BaseResult<PuIdOneVo> puIdOne(PuIdOneDto requestDto) {
        log.info("=============获取平台1.0puId接口入参PuIdOneDto{}",requestDto);
        CuEntity entity = cuMapper.selectById(requestDto.getKmId());
        check(entity);
        CuBasicParam param = tool.getParam(entity);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/puid10/{ssid}/{ssno}", JSON.toJSONString(requestDto), String.class, param.getParamMap());
        log.info("获取平台1.0puId接口中间件响应{}",s);
        CuResponse response = JSONObject.parseObject(s, CuResponse.class);
        String errorMsg = "获取平台1.0puId失败:{},{},{}";
        responseUtil.handleCuRes(errorMsg,DeviceErrorEnum.CU_Pu_ID_ONE_ERROR,response);
        PuIdOneVo vo = JSON.parseObject(s, PuIdOneVo.class);
        return BaseResult.succeed("获取平台1.0puId成功",vo);
    }

    @Override
    public BaseResult<PuIdByOneVo> puIdByOne(PuIdByOneDto requestDto) {
        log.info("=============根据平台1.0PuId获取平台2.0puId接口入参PuIdByOneDto{}",requestDto);
        CuEntity devEntity = cuMapper.selectById(requestDto.getKmId());
        check(devEntity);
        Integer ssid = devEntity.getSsid();
        CuSession cuSession = cuDeviceLoadThread.getCuClient().getSessionManager().getSessionBySSID(ssid);
        PDevice device = cuSession.getDeviceCache().getDeviceByOne(requestDto.getPuId());
        if(ObjectUtils.isEmpty(device)){
            log.error("根据平台puId查询不到设备，请检查参数是否正确");
            throw new CuException(DeviceErrorEnum.GET_CU_DEVICE_INFO_ERROR);
        }
        PuIdByOneVo vo = new PuIdByOneVo();
        vo.setPuId(device.getPuId());
        return BaseResult.succeed( "根据平台1.0PuId获取平台2.0puId成功",vo);
    }

    @Override
    public BaseResult<DevEntityVo> cuGroup(CuRequestDto requestDto) {
        log.info("==============获取cu分组集合入参CuRequestDto{}",requestDto);
        CuEntity devEntity = cuMapper.selectById(requestDto.getKmId());
        check(devEntity);
        if(cuDeviceStatusPoll.get(requestDto.getKmId())==null){
            log.error("获取cu分组集合失败,设备未加载完成{}");
            throw new CuException(DeviceErrorEnum.GET_CU_GROUP_ERROR);
        }
        Integer ssid = devEntity.getSsid();
        //更新设备在线总数分组中设备总数
        cuDeviceLoadThread.updateDeviceStatusCount(ssid);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error("==========获取根分组更新设备状态时睡眠失败{}",e);
        }
        List<PGroup> groupList = this.getGroupList(ssid);
        List<CuGroupVo> collect = groupList.stream().map(pGroup -> convert.covertToCuGroupVo(pGroup)).collect(Collectors.toList());
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
        devEntityVo.setChildList(collect);
        //记录操作日志
        logUtil.operateLog(modelName,"获取监控平台根分组信息成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("获取分组信息成功",devEntityVo);
    }

    @Override
    public BaseResult<List<CuDeviceVo>> cuDevice(CuDevicesDto requestDto) {
        log.info("==============获取cu设备集合入参CuDevicesDto{}",requestDto);
        CuEntity devEntity = cuMapper.selectById(requestDto.getKmId());
        check(devEntity);
        if(cuDeviceStatusPoll.get(requestDto.getKmId())==null){
            log.error("获取cu设备信息失败,设备未加载完成{}");
            throw new CuException(DeviceErrorEnum.GET_CU_GROUP_ERROR);
        }
        Integer ssid = devEntity.getSsid();
        String groupId = requestDto.getGroupId();
        List<PDevice> deviceList = this.getDeviceList(ssid, groupId);
        log.debug("获取到的设备信息为：{}",deviceList);
        List<CuDeviceVo> collect = new ArrayList<>();
        Iterator<PDevice> iterator = deviceList.iterator();
        while (iterator.hasNext()){
            PDevice next = iterator.next();
            if(ObjectUtil.isNotNull(next)){
                CuDeviceVo cuDeviceVo = convert.covertToCuDeviceVo(next);
                List<SrcChn> srcChns = next.getSrcChns();
                if(CollectionUtil.isNotEmpty(srcChns)){
                    List<CuChannelVo> chnCollect = srcChns.stream().map(a -> convert.convertToCuChannelVo(a)).collect(Collectors.toList());
                    cuDeviceVo.setChildList(chnCollect);
                }
                collect.add(cuDeviceVo);
            }
        }
        //记录操作日志
        logUtil.operateLog(modelName,"根据组ID获取设备信息成功成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("获取设备信息成功",collect);
    }

    @Override
    public BaseResult<List<CuGroupVo>> cuGroupById(CuGroupDto requestDto) {
       log.info("==========查询子分组接口入参CuGroupDto:",requestDto);
        CuEntity cuEntity = cuMapper.selectById(requestDto.getKmId());
        check(cuEntity);
        if(cuDeviceStatusPoll.get(requestDto.getKmId())==null){
            log.error("获取cu设备信息失败,设备未加载完成{}");
            throw new CuException(DeviceErrorEnum.GET_CU_GROUP_ERROR);
        }
        PGroup pGroup = cuDeviceLoadThread.getPGroupById(cuEntity.getSsid(), requestDto.getGroupId());
        List<PGroup> sortChildGroups = pGroup.getSortChildGroups();
        List<CuGroupVo> collect = sortChildGroups.stream().map(a -> convert.covertToCuGroupVo(a)).collect(Collectors.toList());
        //记录操作日志
        logUtil.operateLog(modelName,"获取子分组成功",HttpServletRequest.getHeader("Authorization"));
        return BaseResult.succeed("获取子分组成功", collect);
    }

    /**
     * 用于过滤过滤条件后为空的子分组
     *
     * @param devEntityVo
     * @return
     */
    private DevEntityVo removeEmptySortGroup(DevEntityVo devEntityVo) {
        List<CuGroupVo> childList = devEntityVo.getChildList();
        if(CollectionUtil.isEmpty(childList)){
            return devEntityVo;
        }
        for (CuGroupVo cuGroupVo : childList) {
            List<CuGroupVo> sortChildGroups = cuGroupVo.getSortChildGroups();
            Iterator<CuGroupVo> iterator = sortChildGroups.iterator();
            while (iterator.hasNext()) {
                CuGroupVo next = iterator.next();
                if (next.getChildList().size() <= 0) {
                    iterator.remove();
                }
            }
            cuGroupVo.setSortChildGroups(sortChildGroups);
        }
        devEntityVo.setChildList(childList);
        return devEntityVo;
    }

    /**
     * 获取监控平台信息
     *
     * @return
     */
    private DevEntityVo getDevEntityVo(Integer id) throws KMException {
        CuEntity devEntity = cuMapper.selectById(id);
        Integer ssid = devEntity.getSsid();
        List<PGroup> groupList = this.getGroupList(ssid);
        List<CuGroupVo> cuGroupVoList = new ArrayList<>();
        groupList.stream().forEach(cuGroup -> cuGroupVoList.add(convert.covertToCuGroupVo(cuGroup)));
        cuGroupVoList.stream().forEach(cuGroupVo -> cuGroupVo.setUuid(UUID.randomUUID().toString()));
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
        //递归得到分组集合
        List<CuGroupVo> groupVoList = getGroupVoList(cuGroupVoList, ssid);
        devEntityVo.setChildList(groupVoList);
        return devEntityVo;
    }

    /**
     * 根据设备类型和名称得到树状图信息
     *
     * @param id 设备数据库ID
     * @param deviceType 设备类型
     * @param name 设备名称
     */
    private DevEntityVo getDevEntityVoByDeviceTypeAndName(Integer id, Integer deviceType, String name) throws KMException {
        CuEntity devEntity = cuMapper.selectById(id);
        Integer ssid = devEntity.getSsid();
        List<PGroup> groupList = this.getGroupList(ssid);
        List<CuGroupVo> cuGroupVoList = new ArrayList<>();
        groupList.stream().forEach(cuGroup -> cuGroupVoList.add(convert.covertToCuGroupVo(cuGroup)));
        cuGroupVoList.forEach(cuGroupVo -> cuGroupVo.setUuid(UUID.randomUUID().toString()));
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
        //递归得到过滤分组集合
        List<CuGroupVo> groupVoList = getGroupVoListByDeviceTypeAndName(deviceType, cuGroupVoList, ssid, name);
        devEntityVo.setChildList(groupVoList);
        return devEntityVo;
    }

    /**
     * 根据名称和状态得到树状图信息
     *
     * @param id
     * @param name
     * @param status
     */
    private DevEntityVo getDevEntityVoByNameAndStatus(Integer id, String name, Integer status) throws KMException {
        Integer onLine;
        if (status == 1) {
            onLine = 1;
        } else {
            onLine = 0;
        }
        CuEntity devEntity = cuMapper.selectById(id);
        if (devEntity == null) {
            return null;
        }
        Integer ssid = devEntity.getSsid();
        List<PGroup> groupList = this.getGroupList(ssid);
        List<CuGroupVo> cuGroupVoList = new ArrayList<>();
        groupList.stream().forEach(cuGroup -> cuGroupVoList.add(convert.covertToCuGroupVo(cuGroup)));
        cuGroupVoList.forEach(cuGroupVo -> cuGroupVo.setUuid(UUID.randomUUID().toString()));
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
        //递归得到过滤分组集合
        List<CuGroupVo> groupVoList = getGroupVoListByNameAndStatus(name, cuGroupVoList, ssid, onLine);
        devEntityVo.setChildList(groupVoList);
        return devEntityVo;
    }

    /**
     * 根据名称模糊查询得到树状信息
     *
     * @param id
     * @param name
     * @return
     * @throws KMException
     */
    private DevEntityVo getDevEntityVoByName(Integer id, String name) throws KMException {
        CuEntity devEntity = cuMapper.selectById(id);
        if (devEntity == null) {
            return null;
        }
        Integer ssid = devEntity.getSsid();
        List<PGroup> groupList = this.getGroupList(ssid);
        List<CuGroupVo> cuGroupVoList = new ArrayList<>();
        groupList.stream().forEach(cuGroup -> cuGroupVoList.add(convert.covertToCuGroupVo(cuGroup)));
        cuGroupVoList.forEach(cuGroupVo -> cuGroupVo.setUuid(UUID.randomUUID().toString()));
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
        //递归得到过滤分组集合
        List<CuGroupVo> groupVoList = getGroupVoListByName(name, cuGroupVoList, ssid);
        devEntityVo.setChildList(groupVoList);
        return devEntityVo;
    }

    /**
     * 获取监控平台过滤信息Vo
     *
     * @return
     */
    private DevEntityVo getDevEntityVoFilter(Integer id, Integer status) throws KMException {
        Integer onLine;
        if (status == 1) {
            onLine = 1;
        } else {
            onLine = 0;
        }
        CuEntity devEntity = cuMapper.selectById(id);
        Integer ssid = devEntity.getSsid();
        List<PGroup> groupList = this.getGroupList(ssid);
        List<CuGroupVo> cuGroupVoList = new ArrayList<>();
        groupList.stream().forEach(cuGroup -> cuGroupVoList.add(convert.covertToCuGroupVo(cuGroup)));
        cuGroupVoList.forEach(cuGroupVo -> cuGroupVo.setUuid(UUID.randomUUID().toString()));
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
        //递归得到过滤分组集合
        List<CuGroupVo> groupVoList = getGroupVoListFilter(onLine, cuGroupVoList, ssid);
        devEntityVo.setChildList(groupVoList);
        return devEntityVo;
    }

    /**
     * 根据设备类型和状态得到树状图信息
     *
     * @param id 设备数据库ID
     * @param deviceType 设备类型
     * @param status 在线状态
     */
    private DevEntityVo getDevEntityVoByDeviceTypeAndStatus(Integer id, Integer deviceType, Integer status) throws KMException {
        Integer onLine;
        if (status == 1) {
            onLine = 1;
        } else {
            onLine = 0;
        }
        CuEntity devEntity = cuMapper.selectById(id);
        Integer ssid = devEntity.getSsid();
        List<PGroup> groupList = this.getGroupList(ssid);
        List<CuGroupVo> cuGroupVoList = new ArrayList<>();
        groupList.stream().forEach(cuGroup -> cuGroupVoList.add(convert.covertToCuGroupVo(cuGroup)));
        cuGroupVoList.forEach(cuGroupVo -> cuGroupVo.setUuid(UUID.randomUUID().toString()));
        DevEntityVo devEntityVo = convert.convertToDevEntityVo(devEntity);
        //根据在线状态（在线，离线，全部）递归得到过滤分组集合
        List<CuGroupVo> groupVoList = null;
        if(status==0){
            groupVoList =  getGroupVoListByDeviceType(deviceType,cuGroupVoList,ssid);
        }else {
            groupVoList = getGroupVoListByDeviceTypeAndStatus(deviceType, cuGroupVoList, status, onLine);
        }

        devEntityVo.setChildList(groupVoList);
        return devEntityVo;
    }

    /**
     * 递归查询该分组下是否还有子分组，并设置cuDevice列表
     *
     * @param cuGroupVoList
     * @param ssid           查询设备列表所用
     * @return
     * @throws KMException
     */
    private List<CuGroupVo> getGroupVoList(List<CuGroupVo> cuGroupVoList, Integer ssid) {
        if (CollectionUtil.isNotEmpty(cuGroupVoList)) {
            for (CuGroupVo cuGroupVo : cuGroupVoList) {
                setDevList(ssid, cuGroupVo);
            }
            for (CuGroupVo cuGroupVo : cuGroupVoList) {
                List<CuGroupVo> sortChildGroups = cuGroupVo.getSortChildGroups();
                if (CollectionUtil.isEmpty(sortChildGroups)) {
                    continue;
                }
                this.removeEmptyGroup(sortChildGroups, ssid);
                sortChildGroups.stream().forEach(cuGroupVo1 -> cuGroupVo1.setUuid(UUID.randomUUID().toString()));
                getGroupVoList(sortChildGroups, ssid);
            }
        }
        return cuGroupVoList;
    }

    /**
     * 根据设备类型和设备名称模糊查询得到分组
     *
     * @param deviceType
     * @param cuGroupVoList
     * @param ssid
     * @param name
     * @return
     */
    private List<CuGroupVo> getGroupVoListByDeviceTypeAndName(Integer deviceType, List<CuGroupVo> cuGroupVoList, Integer ssid, String name) {
        if (CollectionUtil.isNotEmpty(cuGroupVoList)) {
            Iterator<CuGroupVo> iterator = cuGroupVoList.iterator();
            while (iterator.hasNext()) {
                CuGroupVo next = iterator.next();
                setDeviceListByNameAndDeviceType(deviceType, ssid, next, name);
                List<CuGroupVo> sortChildGroups = next.getSortChildGroups();
                if (CollectionUtil.isEmpty(sortChildGroups)) {
                    continue;
                }
                this.removeEmptyGroup(sortChildGroups, ssid);
                sortChildGroups.stream().forEach(cuGroupVo1 -> cuGroupVo1.setUuid(UUID.randomUUID().toString()));
                getGroupVoListByDeviceTypeAndName(deviceType, sortChildGroups, ssid, name);
            }
        }
        return cuGroupVoList;
    }

    /**
     * 根据名称和状态模糊查询得到分组
     *
     * @param name
     * @param cuGroupVoList
     * @param ssid
     * @return
     */
    private List<CuGroupVo> getGroupVoListByNameAndStatus(String name, List<CuGroupVo> cuGroupVoList, Integer ssid, Integer online) {
        if (CollectionUtil.isNotEmpty(cuGroupVoList)) {
            Iterator<CuGroupVo> iterator = cuGroupVoList.iterator();
            while (iterator.hasNext()) {
                CuGroupVo next = iterator.next();
                setDeviceListByNameAndStatus(name, ssid, next, online);
                List<CuGroupVo> sortChildGroups = next.getSortChildGroups();
                if (CollectionUtil.isEmpty(sortChildGroups)) {
                    continue;
                }
                this.removeEmptyGroup(sortChildGroups, ssid);
                sortChildGroups.stream().forEach(cuGroupVo1 -> cuGroupVo1.setUuid(UUID.randomUUID().toString()));
                getGroupVoListByNameAndStatus(name, sortChildGroups, ssid, online);
            }
        }
        return cuGroupVoList;
    }

    /**
     * 根据设备类型和状态模糊查询得到分组
     *
     * @param deviceType
     * @param cuGroupVoList
     * @param rid
     * @return
     */
    private List<CuGroupVo> getGroupVoListByDeviceTypeAndStatus(Integer deviceType, List<CuGroupVo> cuGroupVoList, Integer rid, Integer online) {
        if (CollectionUtil.isNotEmpty(cuGroupVoList)) {
            Iterator<CuGroupVo> iterator = cuGroupVoList.iterator();
            while (iterator.hasNext()) {
                CuGroupVo next = iterator.next();
                setDeviceListByDeviceTypeAndStatus(deviceType, rid, next, online);
                List<CuGroupVo> sortChildGroups = next.getSortChildGroups();
                if (CollectionUtil.isEmpty(sortChildGroups)) {
                    continue;
                }
                this.removeEmptyGroup(sortChildGroups, rid);
                sortChildGroups.stream().forEach(cuGroupVo1 -> cuGroupVo1.setUuid(UUID.randomUUID().toString()));
                getGroupVoListByDeviceTypeAndStatus(deviceType, sortChildGroups, rid, online);
            }
        }
        return cuGroupVoList;
    }

    /**
     * 根据名称模糊查询的到分组
     *
     * @param name
     * @param cuGroupVoList
     * @param ssid
     * @return
     */
    private List<CuGroupVo> getGroupVoListByName(String name, List<CuGroupVo> cuGroupVoList, Integer ssid) {
        if (CollectionUtil.isNotEmpty(cuGroupVoList)) {
            Iterator<CuGroupVo> iterator = cuGroupVoList.iterator();
            while (iterator.hasNext()) {
                CuGroupVo next = iterator.next();
                setDeviceListByName(name, ssid, next);
                List<CuGroupVo> sortChildGroups = next.getSortChildGroups();
                if (CollectionUtil.isEmpty(sortChildGroups)) {
                    continue;
                }
                this.removeEmptyGroup(sortChildGroups, ssid);
                sortChildGroups.stream().forEach(cuGroupVo1 -> cuGroupVo1.setUuid(UUID.randomUUID().toString()));
                getGroupVoListByName(name, sortChildGroups, ssid);
            }
        }
        return cuGroupVoList;
    }

    /**
     * 设置CuGroupVO中的device列表
     *
     * @param ssid
     * @param cuGroupVo
     * @throws KMException
     */
    private void setDevList(Integer ssid, CuGroupVo cuGroupVo) {
        int devOnlineCount = 0;
        String groupId = cuGroupVo.getId();
        List<PDevice> deviceList = this.getDeviceList(ssid, groupId);
        List<CuDeviceVo> cuDeviceVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(deviceList)) {

            int chanelOnlineCount = 0;
            for (PDevice cuDevice : deviceList) {
                if (cuDevice == null) {
                    continue;
                }
                CuDeviceVo cuDeviceVo = convert.covertToCuDeviceVo(cuDevice);
                cuDeviceVo.setUuid(UUID.randomUUID().toString());
                if (cuDeviceVo.getOnline()==1) {
                    devOnlineCount += 1;
                }
                List<SrcChn> channels = cuDevice.getChannels();
                List<CuChannelVo> cuChannelVos = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(channels)) {
                    for (SrcChn channel : channels) {
                        if (channel.getOnline()==1) {
                            chanelOnlineCount += 1;
                        }
                        if (channel != null) {
                            CuChannelVo cuChannelVo = convert.convertToCuChannelVo(channel);
                            cuChannelVo.setUuid(UUID.randomUUID().toString());
                            cuChannelVos.add(cuChannelVo);
                        }
                    }
                }
                cuDeviceVo.setChildList(cuChannelVos);
                cuDeviceVo.setOnLineCount(chanelOnlineCount);
                cuDeviceVo.setCount(cuChannelVos.size());
                cuDeviceVos.add(cuDeviceVo);
            }
        }
        cuGroupVo.setCount(deviceList.size());
        cuGroupVo.setOnLineCount(devOnlineCount);
        cuGroupVo.setChildList(cuDeviceVos);
    }

    /**
     * 根据设备名称和设备类型获取设备列表
     *
     * @param deviceType
     * @param ssid
     * @param cuGroupVo
     * @param name
     */
    private void setDeviceListByNameAndDeviceType(Integer deviceType, Integer ssid, CuGroupVo cuGroupVo, String name) {
        List<PDevice> deviceList = this.getDeviceList(ssid, cuGroupVo.getId());
        ArrayList<CuDeviceVo> cuDeviceVos = new ArrayList<>();
        deviceList.stream().forEach(cuDevice -> cuDeviceVos.add(convert.covertToCuDeviceVo(cuDevice)));
        cuDeviceVos.stream().forEach(cuDeviceVo -> cuDeviceVo.setUuid(UUID.randomUUID().toString()));
        Iterator<CuDeviceVo> iterator = cuDeviceVos.iterator();
        Integer cuGroupOnLine = 0;
        while (iterator.hasNext()) {
            Integer cuDeviceOnLine = 0;
            CuDeviceVo next = iterator.next();
            //先判断设备是否符合筛选条件
            if (!(next.getType() == deviceType)) {
                iterator.remove();
                continue;
            } else {
                if (next.getOnline()==1) {
                    cuGroupOnLine = cuGroupOnLine + 1;
                }
            }
            //再判断通道是否符合筛选条件
            List<CuChannelVo> childList = next.getChildList();
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setUuid(UUID.randomUUID().toString()));
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setRid(ssid));
            if (CollectionUtil.isNotEmpty(childList)) {
                Iterator<CuChannelVo> iterator1 = childList.iterator();
                while (iterator1.hasNext()) {
                    CuChannelVo next1 = iterator1.next();
                    if (!(next1.getName().toLowerCase().contains(name.toLowerCase()))) {
                        iterator1.remove();
                    } else {
                        if (next1.getOnline()==1) {
                            cuDeviceOnLine = cuDeviceOnLine + 1;
                        }
                    }
                }
            }
            //如果筛选后的通道集合长度大于零给设备设置通道属性值
            if (childList.size() > 0) {
                next.setOnLineCount(cuDeviceOnLine);
                next.setCount(childList.size());
                next.setChildList(childList);
                if (next.getOnline()==1) {
                    cuGroupOnLine = cuGroupOnLine + 1;
                }
            }else {
                //如果筛选后的通道集合长度小于零再对设备名称进行筛选,如果不符合条件则删除改设备
                if(!(next.getName().toLowerCase().contains(name.toLowerCase()))){
                    iterator.remove();
                }
            }
        }
        cuGroupVo.setOnLineCount(cuGroupOnLine);
        cuGroupVo.setCount(cuDeviceVos.size());
        cuGroupVo.setChildList(cuDeviceVos);
    }

    /**
     * 根据状态和名称获取设备列表
     *
     * @param name
     * @param ssid
     * @param cuGroupVo
     * @param online
     */
    private void setDeviceListByNameAndStatus(String name, Integer ssid, CuGroupVo cuGroupVo, Integer online) {
        List<PDevice> deviceList = this.getDeviceList(ssid, cuGroupVo.getId());
        ArrayList<CuDeviceVo> cuDeviceVos = new ArrayList<>();
        deviceList.stream().forEach(cuDevice -> cuDeviceVos.add(convert.covertToCuDeviceVo(cuDevice)));
        cuDeviceVos.stream().forEach(cuDeviceVo -> cuDeviceVo.setUuid(UUID.randomUUID().toString()));
        Iterator<CuDeviceVo> iterator = cuDeviceVos.iterator();
        Integer cuGroupOnLine = 0;
        while (iterator.hasNext()) {
            Integer cuDeviceOnLine = 0;
            CuDeviceVo next = iterator.next();
            List<CuChannelVo> childList = next.getChildList();
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setUuid(UUID.randomUUID().toString()));
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setRid(ssid));
            if (CollectionUtil.isNotEmpty(childList)) {
                Iterator<CuChannelVo> iterator1 = childList.iterator();
                while (iterator1.hasNext()) {
                    CuChannelVo next1 = iterator1.next();
                    if (!(next1.getName().toLowerCase().contains(name.toLowerCase()) && next1.getOnline() == online)) {
                        iterator1.remove();
                    } else {
                        if (next1.getOnline()==1) {
                            cuDeviceOnLine = cuDeviceOnLine + 1;
                        }
                    }
                }
            }
            if (childList.size() > 0) {
                next.setOnLineCount(cuDeviceOnLine);
                next.setCount(childList.size());
                next.setChildList(childList);
                if (next.getOnline()==1) {
                    cuGroupOnLine = cuGroupOnLine + 1;
                }
            } else {
                if (!(next.getName().toLowerCase().contains(name.toLowerCase()) && next.getOnline() == online)) {
                    iterator.remove();
                } else {
                    if (next.getOnline()==1) {
                        cuGroupOnLine = cuGroupOnLine + 1;
                    }
                }
            }
        }
        cuGroupVo.setOnLineCount(cuGroupOnLine);
        cuGroupVo.setCount(cuDeviceVos.size());
        cuGroupVo.setChildList(cuDeviceVos);
    }

    /**
     * 根据名称模糊设置分组设备列表
     *
     * @param name
     * @param ssid
     * @param cuGroupVo
     */
    private void setDeviceListByName(String name, Integer ssid, CuGroupVo cuGroupVo) {
        List<PDevice> deviceList = this.getDeviceList(ssid, cuGroupVo.getId());
        ArrayList<CuDeviceVo> cuDeviceVos = new ArrayList<>();
        deviceList.stream().forEach(cuDevice -> cuDeviceVos.add(convert.covertToCuDeviceVo(cuDevice)));
        cuDeviceVos.stream().forEach(cuDeviceVo -> cuDeviceVo.setUuid(UUID.randomUUID().toString()));
        Iterator<CuDeviceVo> iterator = cuDeviceVos.iterator();
        Integer cuGroupOnLine = 0;
        while (iterator.hasNext()) {
            Integer cuDeviceOnLine = 0;
            CuDeviceVo next = iterator.next();
            List<CuChannelVo> childList = next.getChildList();
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setUuid(UUID.randomUUID().toString()));
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setRid(ssid));
            if (CollectionUtil.isNotEmpty(childList)) {
                Iterator<CuChannelVo> iterator1 = childList.iterator();
                while (iterator1.hasNext()) {
                    CuChannelVo next1 = iterator1.next();
                    if (!next1.getName().toLowerCase().contains(name.toLowerCase())) {
                        iterator1.remove();
                    } else {
                        if (next1.getOnline()==1) {
                            cuDeviceOnLine = cuDeviceOnLine + 1;
                        }
                    }
                }
            }
            if (childList.size() > 0) {
                next.setOnLineCount(cuDeviceOnLine);
                next.setCount(childList.size());
                next.setChildList(childList);
                if (next.getOnline()==1) {
                    cuGroupOnLine = cuGroupOnLine + 1;
                }
            } else {
                if (!next.getName().toLowerCase().contains(name.toLowerCase())) {
                    iterator.remove();
                } else {
                    if (next.getOnline()==1) {
                        cuGroupOnLine = cuGroupOnLine + 1;
                    }
                }
            }
        }
        cuGroupVo.setOnLineCount(cuGroupOnLine);
        cuGroupVo.setCount(cuDeviceVos.size());
        cuGroupVo.setChildList(cuDeviceVos);
    }

    /**
     * 根据状态和设备类型获取设备列表
     *
     * @param deviceType
     * @param ssid
     * @param cuGroupVo
     * @param online
     */
    private void setDeviceListByDeviceTypeAndStatus(Integer deviceType, Integer ssid, CuGroupVo cuGroupVo, Integer online) {
        List<PDevice> deviceList = this.getDeviceList(ssid, cuGroupVo.getId());
        ArrayList<CuDeviceVo> cuDeviceVos = new ArrayList<>();
        deviceList.stream().forEach(cuDevice -> cuDeviceVos.add(convert.covertToCuDeviceVo(cuDevice)));
        cuDeviceVos.stream().forEach(cuDeviceVo -> cuDeviceVo.setUuid(UUID.randomUUID().toString()));
        Iterator<CuDeviceVo> iterator = cuDeviceVos.iterator();
        Integer cuGroupOnLine = 0;
        while (iterator.hasNext()) {
            Integer cuDeviceOnLine = 0;
            CuDeviceVo next = iterator.next();
            //先判断设备是否符合筛选条件
            if (!((next.getType() == deviceType) && next.getOnline() == online)) {
                iterator.remove();
                continue;
            } else {
                if (next.getOnline()==1) {
                    cuGroupOnLine = cuGroupOnLine + 1;
                }
            }
            //再判断通道是否符合筛选条件
            List<CuChannelVo> childList = next.getChildList();
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setUuid(UUID.randomUUID().toString()));
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setRid(ssid));
            if (CollectionUtil.isNotEmpty(childList)) {
                Iterator<CuChannelVo> iterator1 = childList.iterator();
                while (iterator1.hasNext()) {
                    CuChannelVo next1 = iterator1.next();
                    if (!(next1.getOnline() == online)) {
                        iterator1.remove();
                    } else {
                        if (next1.getOnline()==1) {
                            cuDeviceOnLine = cuDeviceOnLine + 1;
                        }
                    }
                }
            }
            //如果筛选后的通道集合长度大于零给设备设置通道属性值
            if (childList.size() > 0) {
                next.setOnLineCount(cuDeviceOnLine);
                next.setCount(childList.size());
                next.setChildList(childList);
            }
        }
        cuGroupVo.setOnLineCount(cuGroupOnLine);
        cuGroupVo.setCount(cuDeviceVos.size());
        cuGroupVo.setChildList(cuDeviceVos);
    }



    /**
     * 递归查询该分组下是否还有子分组，并设置过滤条件cuDevice列表
     *
     * @param cuGroupVoList
     * @param ssid 会话ID
     * @return
     * @throws KMException
     */
    private List<CuGroupVo> getGroupVoListFilter(Integer online, List<CuGroupVo> cuGroupVoList, Integer ssid) {
        if (CollectionUtil.isNotEmpty(cuGroupVoList)) {
            for (CuGroupVo cuGroupVo : cuGroupVoList) {
                setDeviceListFilter(online, ssid, cuGroupVo);
            }
            for (CuGroupVo cuGroupVo : cuGroupVoList) {
                List<CuGroupVo> sortChildGroups = cuGroupVo.getSortChildGroups();
                if (CollectionUtil.isEmpty(sortChildGroups)) {
                    continue;
                }
                this.removeEmptyGroup(sortChildGroups, ssid);
                sortChildGroups.stream().forEach(cuGroupVo1 -> cuGroupVo1.setUuid(UUID.randomUUID().toString()));
                getGroupVoListFilter(online, sortChildGroups, ssid);
            }
        }
        return cuGroupVoList;
    }

    /**
     * 根据设备类型查询得到分组
     *
     * @param deviceType 设备类型
     * @param cuGroupVoList 分组集合
     * @param ssid 会话ID
     * @return
     */
    private List<CuGroupVo> getGroupVoListByDeviceType(Integer deviceType, List<CuGroupVo> cuGroupVoList, Integer ssid) {
        if (CollectionUtil.isNotEmpty(cuGroupVoList)) {
            Iterator<CuGroupVo> iterator = cuGroupVoList.iterator();
            while (iterator.hasNext()) {
                CuGroupVo next = iterator.next();
                setDeviceListByDeviceType(deviceType, ssid, next);
                List<CuGroupVo> sortChildGroups = next.getSortChildGroups();
                if (CollectionUtil.isEmpty(sortChildGroups)) {
                    continue;
                }
                this.removeEmptyGroup(sortChildGroups, ssid);
                sortChildGroups.stream().forEach(cuGroupVo1 -> cuGroupVo1.setUuid(UUID.randomUUID().toString()));
                getGroupVoListByDeviceType(deviceType, sortChildGroups, ssid);
            }
        }
        return cuGroupVoList;
    }

    /**
     * 根据设备类型获取设备列表
     *
     * @param deviceType
     * @param rid
     * @param cuGroupVo
     */
    private void setDeviceListByDeviceType(Integer deviceType, Integer rid, CuGroupVo cuGroupVo) {
        List<PDevice> deviceList = this.getDeviceList(rid, cuGroupVo.getId());
        ArrayList<CuDeviceVo> cuDeviceVos = new ArrayList<>();
        deviceList.stream().forEach(cuDevice -> cuDeviceVos.add(convert.covertToCuDeviceVo(cuDevice)));
        cuDeviceVos.stream().forEach(cuDeviceVo -> cuDeviceVo.setUuid(UUID.randomUUID().toString()));
        Iterator<CuDeviceVo> iterator = cuDeviceVos.iterator();
        Integer cuGroupOnLine = 0;
        while (iterator.hasNext()) {
            Integer cuDeviceOnLine = 0;
            CuDeviceVo next = iterator.next();
            //先筛选设备
            if (next.getType() != deviceType) {
                iterator.remove();
                continue;
            } else {
                if (next.getOnline()==1) {
                    cuGroupOnLine = cuGroupOnLine + 1;
                }
            }
            //判断通道是否符合筛选条件
            List<CuChannelVo> childList = next.getChildList();
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setUuid(UUID.randomUUID().toString()));
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setRid(rid));
            if (CollectionUtil.isNotEmpty(childList)) {
                Iterator<CuChannelVo> iterator1 = childList.iterator();
                while (iterator1.hasNext()) {
                    CuChannelVo next1 = iterator1.next();
                    if (next1.getOnline()==1) {
                        cuDeviceOnLine = cuDeviceOnLine + 1;
                    }
                }
            }
            //如果筛选后的通道集合长度大于零给设备设置通道属性值
            if (childList.size() > 0) {
                next.setOnLineCount(cuDeviceOnLine);
                next.setCount(childList.size());
                next.setChildList(childList);
            }
        }
        cuGroupVo.setOnLineCount(cuGroupOnLine);
        cuGroupVo.setCount(cuDeviceVos.size());
        cuGroupVo.setChildList(cuDeviceVos);
    }

    /**
     * 过滤在线或者不在线的设备
     *
     * @param online
     * @param //id
     * @return
     */
    private void setDeviceListFilter(Integer online, Integer ssid, CuGroupVo cuGroupVo) {
        List<PDevice> deviceList = this.getDeviceList(ssid, cuGroupVo.getId());
        ArrayList<CuDeviceVo> cuDeviceVos = new ArrayList<>();
        deviceList.stream().forEach(cuDevice -> cuDeviceVos.add(convert.covertToCuDeviceVo(cuDevice)));
        cuDeviceVos.stream().forEach(cuDeviceVo -> cuDeviceVo.setUuid(UUID.randomUUID().toString()));
        Iterator<CuDeviceVo> iterator = cuDeviceVos.iterator();
        Integer cuGroupOnLine = 0;
        while (iterator.hasNext()) {
            Integer cuDeviceOnLine = 0;
            CuDeviceVo next = iterator.next();
            if (next.getOnline() != online) {
                iterator.remove();
                continue;
            } else {
                cuGroupOnLine = cuGroupOnLine + 1;
            }
            List<CuChannelVo> childList = next.getChildList();
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setUuid(UUID.randomUUID().toString()));
            childList.stream().forEach(cuChannelVo -> cuChannelVo.setRid(ssid));
            if (CollectionUtil.isNotEmpty(childList)) {
                Iterator<CuChannelVo> iterator1 = childList.iterator();
                while (iterator1.hasNext()) {
                    CuChannelVo next1 = iterator1.next();
                    if (next1.getOnline() != online) {
                        iterator1.remove();
                    } else {
                        cuDeviceOnLine = cuDeviceOnLine + 1;
                    }
                }
            }
            next.setOnLineCount(cuDeviceOnLine);
            next.setCount(childList.size());
            next.setChildList(childList);
        }
        cuGroupVo.setOnLineCount(cuGroupOnLine);
        cuGroupVo.setCount(cuDeviceVos.size());
        cuGroupVo.setChildList(cuDeviceVos);
    }

    /**
     * 根据ssid获取分组
     * @param ssid 登录监控平台返回的ID
     * @return
     * @throws KMException
     */
    public List<PGroup> getGroupList(Integer ssid) throws KMException {

        CuSession cuSession = cuDeviceLoadThread.getCuClient().getSessionManager().getSessionBySSID(ssid);
        return cuSession.getDeviceCache().getGroups();
    }

    /**
     * 根据分组ID获取设备列表
     * @param ssid 登录监控平台返回的ID
     * @param groupId 分组ID
     * @return
     * @throws KMException
     */
    public List<PDevice> getDeviceList(Integer ssid, String groupId) throws KMException {

        log.info("根据分组ID获取设备列表入参ssid:{},groupId:{}",ssid,groupId);
        CuSession cuSession = cuDeviceLoadThread.getCuClient().getSessionManager().getSessionBySSID(ssid);
        List<PDevice> pDeviceList;
        if (StringUtils.isEmpty(groupId)) {
            pDeviceList = cuSession.getDeviceCache().getDevices();

        } else {
            pDeviceList = cuSession.getDeviceCache().getDeivcesByGroupId(groupId);
        }
        return pDeviceList;
    }



    /**
     * 剔除空分组（分组底下未挂载任何设备）
     *
     * @param cuGroupVos
     * @param ssid
     */
    private void removeEmptyGroup(List<CuGroupVo> cuGroupVos, Integer ssid) {
        Iterator<CuGroupVo> iterator = cuGroupVos.iterator();
        while (iterator.hasNext()) {
            CuGroupVo next = iterator.next();
            String id = next.getId();
            //得到子分组
            List<CuGroupVo> groups = next.getSortChildGroups();
            //得到设备列表
            List<PDevice> deviceList = this.getDeviceList(ssid, id);
            //如果分组底下不含设备列表和子分组则删除此分组
            if (CollectionUtil.isEmpty(deviceList)&&CollectionUtil.isEmpty(groups)) {
                iterator.remove();
            }
        }
    }

}
