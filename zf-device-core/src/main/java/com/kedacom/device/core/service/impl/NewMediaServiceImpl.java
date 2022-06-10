package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.device.core.basicParam.*;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.NewMediaConvert;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.convert.UmsGroupConvert;
import com.kedacom.device.core.entity.GroupInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.enums.DeviceModelType;
import com.kedacom.device.core.exception.CuException;
import com.kedacom.device.core.exception.NewMediaException;
import com.kedacom.device.core.mapper.NewMediaMapper;
import com.kedacom.device.core.notify.nm.NewMediaDeviceCache;
import com.kedacom.device.core.notify.nm.NewMediaDeviceLoadThread;
import com.kedacom.device.core.notify.nm.pojo.NmGroup;
import com.kedacom.device.core.service.NewMediaService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import com.kedacom.newMedia.dto.AudioCapDTO;
import com.kedacom.newMedia.dto.NMDeviceListDto;
import com.kedacom.newMedia.dto.NewMediaLoginDto;
import com.kedacom.newMedia.dto.SVROrderDTO;
import com.kedacom.newMedia.entity.NewMediaEntity;
import com.kedacom.newMedia.pojo.BurnInfo;
import com.kedacom.newMedia.pojo.NMDevice;
import com.kedacom.newMedia.resopnse.*;
import com.kedacom.streamMedia.request.GetAudioCapDTO;
import com.kedacom.streamMedia.request.GetBurnStateDTO;
import com.kedacom.streamMedia.request.GetSvrAudioActStateDTO;
import com.kedacom.streamMedia.response.GetAudioCapVO;
import com.kedacom.streamMedia.response.GetBurnStateVO;
import com.kedacom.streamMedia.response.GetSvrAudioActStateVo;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ycw
 * @describe
 * @date 2022/04/02
 */
@Slf4j
@Service
public class NewMediaServiceImpl implements NewMediaService {

    @Resource
    private NewMediaMapper mapper;

    @Resource
    private NewMediaConvert convert;

    @Resource
    private HandleResponseUtil responseUtil;

    @Resource
    private RemoteRestTemplate remoteRestTemplate;

    private String newMediaNtyUrl = "127.0.0.1:9000";

    private final static String REQUEST_HEAD = "http://";

    private final static String NOTIFY_URL = "/api/api-device/ums/device/notify";

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    //新媒体访问地址
    private final static String NEW_MEDIA = "/mid/v2/unit";


    //newMedia状态池 若成功登录则把数据库ID和登录状态放入此池中1为已登录，若登出或者删除则从此状态池中移除
    public static ConcurrentHashMap<Integer, Integer> newMediaStatusPoll = new ConcurrentHashMap<>();

    //新媒体设备状态池 若设备加载完则把数据库ID和状态放入此池中1为已加载完所以设备，若登出则从此状态池中移除
    public static ConcurrentHashMap<Integer, Integer> nmDeviceStatusPoll = new ConcurrentHashMap<>();

    //新媒体心跳状态池 若登出或者掉线则从此状态池中移除
    public static ConcurrentHashMap<Integer, ScheduledThreadPoolExecutor> hbStatusPoll = new ConcurrentHashMap<>();

    /**
     * 对名称和IP做唯一校验
     *
     * @return
     */
    public boolean isAddRepeat(UmsDeviceInfoAddRequestDto dto) {
        String ip = dto.getDeviceIp();
        String name = dto.getName();
        LambdaQueryWrapper<NewMediaEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewMediaEntity::getDevIp, ip).or().eq(NewMediaEntity::getName, name);
        List<NewMediaEntity> devEntitiesInsert = mapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(devEntitiesInsert)) {
            log.info("=============添加新媒体时IP或名称重复===============");
            return false;
        }
        return true;
    }

    /**
     * 对名称和IP做唯一校验
     *
     * @return
     */
    public boolean isUpdateRepeat(UmsDeviceInfoUpdateRequestDto dto) {
        String ip = dto.getDeviceIp();
        String name = dto.getName();
        LambdaQueryWrapper<NewMediaEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewMediaEntity::getDevIp, ip).ne(NewMediaEntity::getId, Integer.valueOf(dto.getId()));
        List<NewMediaEntity> devEntitiesUpdate = mapper.selectList(wrapper);
        LambdaQueryWrapper<NewMediaEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NewMediaEntity::getName, name).ne(NewMediaEntity::getId, Integer.valueOf(dto.getId()));
        List<NewMediaEntity> devEntities = mapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(devEntitiesUpdate) || CollectionUtil.isNotEmpty(devEntities)) {
            log.info("==================修改新媒体平台时IP或名称重复========================");
            return false;
        }
        return true;
    }

    public String getUrl() {
        String url = REQUEST_HEAD + kmProxy + NEW_MEDIA;
        return url;
    }

    public NewMediaBasicParam getParam(Integer ssid) {
        NewMediaBasicParam param = new NewMediaBasicParam();
        Map<String, Long> paramMap = new HashMap<>();
        Long s = Long.valueOf(ssid);
        Long n = (long) NumGen.getNum();
        paramMap.put("ssid", s);
        paramMap.put("ssno", n);
        param.setParamMap(paramMap);
        param.setUrl(getUrl());

        return param;
    }


    @Override
    public void initNM() {
        log.info("===========服务启动登录新媒体");
        try {
            this.loginById(DevTypeConstant.updateRecordKey);
        } catch (ExecutionException e) {
            log.error("===========服务启动登录新媒体失败{}", e);
        } catch (InterruptedException e) {
            log.error("===========服务启动登录新媒体失败{}", e);
        }
    }

    public boolean checkDevice(){
        if (nmDeviceStatusPoll.get(DevTypeConstant.updateRecordKey) == null) {
            log.error("获取新媒体设备信息失败,设备未加载完成{}");
            return false;
        }
        return true;
    }

    @Override
    public BaseResult<String> insertUmsDevice(UmsDeviceInfoAddRequestDto requestDto) {

        log.info("新增新媒体平台信息参数 ： requestDto {}", requestDto);
        NewMediaEntity n = mapper.selectById(1);
        if (ObjectUtil.isNotNull(n)) {
           return BaseResult.failed("===已有新媒体设备请勿重复添加");
        }
        NewMediaEntity entity = new NewMediaEntity();
        entity.setId(1);
        entity.setName(requestDto.getName());
        entity.setDevType(DeviceModelType.NM.getCode());
        entity.setDevIp(requestDto.getDeviceIp());
        entity.setDevPort(requestDto.getDevicePort());
        entity.setNotifyIp(requestDto.getDeviceNotifyIp());
        entity.setMediaScheduleIp(requestDto.getMediaIp());
        entity.setMediaSchedulePort(requestDto.getMediaPort());
        entity.setNmediaIp(requestDto.getStreamingMediaIp());
        entity.setNmediaPort(requestDto.getStreamingMediaPort());
        entity.setRecPort(requestDto.getStreamingMediaRecPort());
        entity.setMspAccount(requestDto.getMspAccount());
        entity.setMspPassword(requestDto.getMspPassword());
        mapper.insert(entity);
        try {
            CompletableFuture.runAsync(() -> logoutById(1));
        } catch (Exception e) {
            log.error("=========新增新媒体时登录失败，失败原因为{}", e);
        }
        return BaseResult.succeed("新增新媒体平台成功");
    }

    public Integer loginById(Integer id) throws ExecutionException, InterruptedException {
        synchronized (this) {
            log.info("登录新媒体平台入参信息:{}", id);
            //登录之前先判断改平台是否已经登录，如果已经登录则无需登录
            if (newMediaStatusPoll.get(id) != null) {
                return DevTypeConstant.updateRecordKey;
            }
            RestTemplate template = remoteRestTemplate.getRestTemplate();
            NewMediaEntity entity = mapper.selectById(id);
            if (entity == null) {
                throw new NewMediaException(DeviceErrorEnum.DEVICE_NOT_FOUND);
            }
            NewMediaLoginDto request = convert.convertToNewMediaLoginDto(entity);
            String ntyUrl = REQUEST_HEAD + newMediaNtyUrl + NOTIFY_URL;
            request.setNtyUrl(ntyUrl);
            String url = getUrl();
            Map<String, Long> paramMap = new HashMap<>();
            paramMap.put("ssno", (long) NumGen.getNum());

            log.info("登录新媒体中间件入参信息:{},登录的ssno为：{}", JSON.toJSONString(request), paramMap);
            String string = template.postForObject(url + "/login/{ssno}", JSON.toJSONString(request), String.class, paramMap);
            log.info("登录新媒体中间件应答:{}", string);

            NewMediaLoginResponse response = JSON.parseObject(string, NewMediaLoginResponse.class);
            //如果是密码错误或者是用户不存在首先去除定时任务不进行无限重连
            if (response.getCode() != 0) {
                log.error("=============登录新媒体失败错误码为：{}", response.getCode());
                return DevTypeConstant.updateRecordKey;
            }
            //如果登录成功再把ssid保存进数据库
            entity.setSsid(response.getSsid());
            entity.setModifyTime(new Date());
            mapper.updateById(entity);
            //登录成功以后加载分组和设备信息
           CompletableFuture.runAsync(()->loadDevice(entity));
            //往新媒体状态池放入当前状态 1已登录
            newMediaStatusPoll.put(entity.getId(), DevTypeConstant.updateRecordKey);
            //发送心跳
            hbTask(entity.getId());
            return DevTypeConstant.getZero;
        }
    }

    private void getGroup(NewMediaEntity entity) {
        log.info("==========获取分组=========");
        NewMediaBasicParam param = getParam(entity.getSsid());
        log.info("获取新媒体分组ssid/ssno{}", param);
        ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/devgrouplist/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
        log.info("获取新媒体分组中间件响应:{}", exchange.getBody());
    }

    public void loadDevice(NewMediaEntity entity){
        getGroup(entity);
        Integer code = getDevice(entity);
        if (code != 1) {
            NewMediaDeviceCache.getInstance().clearDevice();
            log.error("=============第一次加载新媒体设备失败尝试第二次加载");
            CompletableFuture.runAsync(() -> getDevice(entity));
        }
        //设备状态池设置设备加载完成状态
        nmDeviceStatusPoll.put(1,1);
    }

    public Integer getDevice(NewMediaEntity entity) {
        log.info("======开始获取新媒体设备");

        int queryIndex = 0;
        int queryCount = 100;
        int countNum = 0;
        NMDeviceListDto listDto = new NMDeviceListDto();
        listDto.setQueryCount(queryCount);
        for (int i = 0; i <= countNum; i++) {
            listDto.setQueryIndex(queryIndex);
            NMDeviceListResponse response = null;
            try {
                response = getDeviceList(entity.getSsid(), listDto);
            } catch (Exception e) {
                log.error("======新媒体获取设备失败，失败原因为：{}",e);
            }
            if (response.getCode() != 0) {
                log.error("从获取第 {} 页设备信息失败", queryIndex);
                NewMediaDeviceCache.getInstance().clearDevice();
                return 0;
            }
            Integer resultCount = response.getTotal();
            //把设备存入cache
            NewMediaDeviceLoadThread.getInstance().onDevice(response.getDevList());
            if (resultCount < queryCount) {
                return 1;
            }
            countNum++;
            queryIndex++;
        }
        //设备加载完往设备状态池中放入设备ID
        nmDeviceStatusPoll.put(entity.getId(), 1);
        return 1;


    }

    public NMDeviceListResponse getDeviceList(Integer ssid, NMDeviceListDto dto) {
        log.info("======开始获取第{}页设备", dto.getQueryIndex());
        NewMediaBasicParam param = getParam(ssid);
        String s = remoteRestTemplate.getRestTemplate().postForObject(param.getUrl() + "/devlist/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        log.info("=======获取设备中间件应答{}", s);
        NMDeviceListResponse response = JSONObject.parseObject(s, NMDeviceListResponse.class);
        return response;
    }


    @Override
    public void logoutById(Integer id) {
        synchronized (this) {
            log.info("======根据ID登出新媒体平台{}", id);
            NewMediaEntity entity = mapper.selectById(id);
            if (entity.getSsid() == null) {
                return;
            }
            //去除设备锁
            nmDeviceStatusPoll.remove(1);
            //去除底层cache
            NewMediaDeviceCache.getInstance().clear();
            //去除心跳定时任务
            ScheduledThreadPoolExecutor Executor = hbStatusPoll.get(entity.getId());
            if (ObjectUtil.isNotNull(Executor)) {
                Executor.shutdownNow();
                hbStatusPoll.remove(entity.getId());
            }
            //往新媒体状态池移除当前新媒体的id
            newMediaStatusPoll.remove(id);
            //从新媒体设备状态池中去除当前新媒体的ID
            nmDeviceStatusPoll.remove(id);
            NewMediaBasicParam param = getParam(entity.getSsid());
            log.info("根据ID登出新媒体接口入参Id：{},ssid/ssno{}", id, param);
            ResponseEntity<String> exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/login/{ssid}/{ssno}", HttpMethod.DELETE, null, String.class, param.getParamMap());
            log.info("=======登出新媒体应答{}", exchange.getBody());
            NewMediaResponse response = JSONObject.parseObject(exchange.getBody(), NewMediaResponse.class);
            String errorMsg = "登出新媒体失败:{},{},{}";
            responseUtil.handleNewMediaRes(errorMsg, DeviceErrorEnum.NM_LOGOUT_FAILED, response);
            entity.setSsid(null);
            mapper.updateById(entity);
        }

    }

    /**
     * 新媒体维护心跳定时任务 每1分钟发一次心跳
     *
     * @param dbId
     */
    public void hbTask(Integer dbId) {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder().setNameFormat("hbTask-pool-%d").build());
        // 创建定时任务，每1分钟发送一次心跳
        scheduled.scheduleAtFixedRate(() -> {
            hb(dbId);
        }, 1, 60, TimeUnit.SECONDS);//延迟1秒每1分钟发一次心跳
        //往心跳状态池放入当前执行任务的scheduled
        hbStatusPoll.put(dbId, scheduled);
    }

    /**
     * 发送心跳 发送失败以后重试3次 三次都失败则不再发送
     *
     * @param dbId
     * @return
     */
    public BaseResult<String> hb(Integer dbId) {
        log.info("新媒体发送心跳接口入参{}", dbId);
        NewMediaEntity entity = mapper.selectById(dbId);
        check(entity);
        NewMediaBasicParam param = getParam(entity.getSsid());
        log.info("发送新媒体心跳中间件入参ssno/ssid{}", param.getParamMap());
        ResponseEntity<String> exchange = null;
        try {
            exchange = remoteRestTemplate.getRestTemplate().exchange(param.getUrl() + "/hb/{ssid}/{ssno}", HttpMethod.GET, null, String.class, param.getParamMap());
            log.info("发送新媒体心跳中间件响应{}", exchange.getBody());
            NewMediaResponse response = JSONObject.parseObject(exchange.getBody(), NewMediaResponse.class);
            if (response.getCode() != 0) {
                NewMediaReLoginParam p = new NewMediaReLoginParam();
                p.setDbId(dbId);
                this.reTryLogin(p);
                log.error("===============发送新媒体心跳时发生返回错误码不是0，即将进行自动重连，数据库ID为：{},返回的错误码为{}", dbId, response.getCode());
                removeHbTask(dbId);
                return BaseResult.failed("发送新媒体心跳失败");
            }
        } catch (Exception e) {
            log.error("===============发送新媒体心跳时发生异常，即将进行自动重连，数据库ID为：{},错误原因为{}", dbId, e);
            //中间件挂了以后先去除心跳任务
            removeHbTask(dbId);
            //进行重连
            CuReLoginParam paramLogin = new CuReLoginParam();
            paramLogin.setDbId(dbId);
            CompletableFuture.runAsync(() -> ContextUtils.publishReLogin(paramLogin));
            return BaseResult.failed("发送新媒体心跳失败");
        }

        return BaseResult.succeed("发送新媒体心跳成功");
    }

    /**
     * 根据数据库ID自动重连每一分钟重连一次
     *
     * @param paramLogin
     */
    @EventListener
    public void reTryLogin(NewMediaReLoginParam paramLogin) {
        log.info("==================观察者收到新媒体发送心跳失败，即将进行自动重连，数据库ID为：{}", paramLogin.getDbId());
        try {
            this.logoutById(paramLogin.getDbId());
        } catch (Exception e) {
            log.error("===============发送心跳时失败尝试重启新媒体平台的时候先做登出，但登出失败");
        }
        reTryLoginNow(paramLogin.getDbId());
    }

    /**
     * 根据数据库ID自动重连每一分钟重连一次
     *
     * @param dbId
     */
    public void reTryLoginNow(Integer dbId) {
        log.info("=====================新媒体即将进行自动重连数据库ID为：{}", dbId);
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder().setNameFormat("reTryLogin-pool-%d").build());
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer baseResult = loginById(dbId);
                    if (baseResult == 0) {
                        scheduled.shutdownNow();
                    }
                } catch (Exception e) {
                    log.error("=======定时任务重连新媒体平台失败");
                }

            }
        }, 60, 60, TimeUnit.SECONDS);

    }

    /**
     * 去除心跳任务并且状态置为离线
     *
     * @param dbId
     */
    public void removeHbTask(Integer dbId) {
        log.info("==========去除心跳任务");
        ScheduledThreadPoolExecutor scheduled = hbStatusPoll.get(dbId);
        if (ObjectUtil.isNotNull(scheduled)) {
            scheduled.shutdownNow();
        }
        if (newMediaStatusPoll.get(dbId) != null) {
            newMediaStatusPoll.remove(dbId);
        }
    }

    /**
     * 新媒体非空校验以及是否登录校验
     *
     * @param entity
     */
    private void check(NewMediaEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            throw new NewMediaException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if (newMediaStatusPoll.get(entity.getId()) == null) {
            throw new NewMediaException(DeviceErrorEnum.DEVICE_NOT_LOGIN);
        }
    }

    @Override
    public BaseResult<String> updateUmsDevice(UmsDeviceInfoUpdateRequestDto requestDto) {
        log.info("更新统一平台信息参数 ： requestDto {}", requestDto);
        if (!isUpdateRepeat(requestDto)) {
            throw new NewMediaException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
        }
        //更新之前先登出
        try {
            this.logoutById(Integer.valueOf(requestDto.getId()));
        } catch (Exception e) {
            log.error("=====更新新媒体时登出失败{}", e);
        }
        NewMediaEntity entity = mapper.selectById(Integer.valueOf(requestDto.getId()));
        entity.setName(requestDto.getName());
        entity.setDevIp(requestDto.getDeviceIp());
        entity.setDevPort(requestDto.getDevicePort());
        entity.setNotifyIp(requestDto.getDeviceNotifyIp());
        entity.setMediaScheduleIp(requestDto.getMediaIp());
        entity.setMediaSchedulePort(requestDto.getMediaPort());
        entity.setNmediaIp(requestDto.getStreamingMediaIp());
        entity.setNmediaPort(requestDto.getStreamingMediaPort());
        entity.setRecPort(requestDto.getStreamingMediaRecPort());
        entity.setMspAccount(requestDto.getMspAccount());
        entity.setMspPassword(requestDto.getMspPassword());
        mapper.updateById(entity);
        try {
            CompletableFuture.runAsync(() -> logoutById(1));
        } catch (Exception e) {
            log.error("====更新新媒体信息时登录新媒体失败，失败原因为：{}", e);
        }
        return BaseResult.succeed("修改新媒体平台成功");

    }


    @Override
    public BaseResult<String> deleteUmsDevice(UmsDeviceInfoDeleteRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        log.info("删除新媒体平台信息参数 ： requestDto [{}]", umsId);
        //删除之前先登出
        try {
            logoutById(Integer.valueOf(requestDto.getUmsId()));
        } catch (Exception e) {
            log.error("========删除新媒体时登出失败");
        }
        mapper.deleteById(Integer.valueOf(requestDto.getUmsId()));
        return BaseResult.succeed("删除成功");
    }

    @Override
    public BasePage<UmsDeviceInfoSelectResponseDto> selectUmsDeviceList(UmsDeviceInfoSelectRequestDto requestDto) {

        log.info("==========新媒体分页查询{}", requestDto);
        Page<NewMediaEntity> page = new Page<>();
        page.setCurrent(requestDto.getCurPage());
        page.setSize(requestDto.getPageSize());
        //条件查询
        LambdaQueryWrapper<NewMediaEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(requestDto.getName())) {
            queryWrapper.like(NewMediaEntity::getName, requestDto.getName());
        }
        Page<NewMediaEntity> platformEntityPage = mapper.selectPage(page, queryWrapper);
        List<NewMediaEntity> records = platformEntityPage.getRecords();
        //转化为UmsDeviceInfoSelectResponseDto
        ArrayList<UmsDeviceInfoSelectResponseDto> dtos = new ArrayList<>();
        Iterator<NewMediaEntity> iterator = records.iterator();
        while (iterator.hasNext()) {
            NewMediaEntity next = iterator.next();
            UmsDeviceInfoSelectResponseDto dto = new UmsDeviceInfoSelectResponseDto();
            dto.setId(String.valueOf(next.getId()));
            dto.setName(next.getName());
            dto.setDeviceType(String.valueOf(7));
            dto.setDeviceIp(next.getDevIp());
            dto.setDevicePort(next.getDevPort());
            dto.setDeviceNotifyIp(next.getNotifyIp());
            dto.setMediaIp(next.getMediaScheduleIp());
            dto.setMediaPort(next.getMediaSchedulePort());
            dto.setStreamingMediaIp(next.getNmediaIp());
            dto.setStreamingMediaPort(next.getNmediaPort());
            dto.setStreamingMediaRecPort(next.getRecPort());
            dto.setMspAccount(next.getMspAccount());
            dto.setMspPassword(next.getMspPassword());
            dtos.add(dto);
        }
        BasePage<UmsDeviceInfoSelectResponseDto> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(requestDto.getCurPage());
        basePage.setPageSize(requestDto.getPageSize());
        basePage.setData(dtos);
        return basePage;
    }

    @Override
    public UmsDeviceInfoSelectByIdResponseDto getDeviceInfoById(UmsDeviceInfoSelectByIdRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        log.info("根据id查询新媒体平台信息参数 ： requestDto [{}]", umsId);
        NewMediaEntity next = mapper.selectById(Integer.valueOf(umsId));
        if (next == null) {
            log.error("根据id查询新媒体平台信息不存在");
            return null;
        }
        UmsDeviceInfoSelectByIdResponseDto dto = new UmsDeviceInfoSelectByIdResponseDto();
        dto.setId(requestDto.getUmsId());
        dto.setName(next.getName());
        dto.setDeviceType(String.valueOf(7));
        dto.setDeviceIp(next.getDevIp());
        dto.setDevicePort(next.getDevPort());
        dto.setDeviceNotifyIp(next.getNotifyIp());
        dto.setMediaIp(next.getMediaScheduleIp());
        dto.setMediaPort(next.getMediaSchedulePort());
        dto.setStreamingMediaIp(next.getNmediaIp());
        dto.setStreamingMediaPort(next.getNmediaPort());
        dto.setStreamingMediaRecPort(next.getRecPort());
        return dto;
    }

    @Override
    public void syncDeviceData(UmsDeviceInfoSyncRequestDto requestDto) {
        log.info("=========手动同步设备接口入参{}", requestDto);
        try {
            this.logoutById(Integer.valueOf(requestDto.getUmsId()));
            this.loginById(Integer.valueOf(requestDto.getUmsId()));
        } catch (ExecutionException e) {
            log.error("==========手动同步设备失败{}", e);
        } catch (InterruptedException e) {
            log.error("==========手动同步设备失败{}", e);
        }
    }

    @Override
    public BasePage<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceList(UmsSubDeviceInfoQueryRequestDto requestDto) {
        log.info("==查询统一设备平台下挂载的子设备信息接口入参:{}", requestDto);
        if (!check()) {
            return null;
        }
        if (!checkDevice()) {
            throw new NewMediaException(DeviceErrorEnum.GET_NM_GROUP_ERROR);
        }
        List<NMDevice> devices = NewMediaDeviceCache.getInstance().getDevices();
        Stream<NMDevice> stream = devices.stream();

        String deviceName = requestDto.getDeviceName();
        if (StrUtil.isNotBlank(deviceName)) {
            stream = stream.filter(x -> x.getName().toLowerCase().contains(deviceName.toLowerCase()));
        }
        String deviceIp = requestDto.getDeviceIp();
        if (StrUtil.isNotBlank(deviceIp)) {
            stream = stream.filter(x -> x.getIpv4().equals(deviceIp));
        }
        String gbId = requestDto.getGbId();
        if (StrUtil.isNotBlank(gbId)) {
            stream = stream.filter(x -> x.getGbId().equals(gbId));
        }
        String groupId = requestDto.getGroupId();
        if (StrUtil.isNotBlank(groupId)) {
            stream = stream.filter(x -> x.getGroupId().equals(groupId));
        }
        Integer status = requestDto.getStatus();
        if (status != null) {
            stream = stream.filter(x -> x.getStatus() == status);
        }
        List<NMDevice> collect = stream.collect(Collectors.toList());
        ArrayList<NMDevice> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(collect)) {
            Iterator<NMDevice> iterator = collect.iterator();
            while (iterator.hasNext()) {
                NMDevice next = iterator.next();
                List<String> deviceTypeList = requestDto.getDeviceTypeList();
                if (CollectionUtil.isNotEmpty(deviceTypeList)) {
                    Iterator<String> ite = deviceTypeList.iterator();
                    while (ite.hasNext()) {
                        String devType = ite.next();
                        String type = next.getDeviceType();
                        //遍历前端传的设备类型集合只要有其中一种和咱们设备类型一致就把设备加入新的集合
                        if (type.equals(devType)) {
                            list.add(next);
                        }
                    }

                }
            }
        }
        if (CollectionUtil.isEmpty(list)) {
            log.error("查询平台下挂载的子设备为空");
            return null;
        }
        log.info("查询统一平台下挂载的子设备信息 ： records [{}]", list);
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceInfoQueryResponseDtoList = list.stream().map(a -> convert.convertToPage(a)).collect(Collectors.toList());
        //这里有一个非常基础但是容易被忽视的问题:整数相除会造成进度丢失，先转成double类型
        int totalPage = (int) Math.ceil((double) umsSubDeviceInfoQueryResponseDtoList.size() / requestDto.getPageSize());
        BasePage<UmsSubDeviceInfoQueryResponseDto> basePage = new BasePage<>();
        basePage.setTotal(Long.valueOf(umsSubDeviceInfoQueryResponseDtoList.size()));
        basePage.setTotalPage(Long.valueOf(totalPage));
        basePage.setCurPage(requestDto.getCurPage());
        basePage.setPageSize(requestDto.getPageSize());
        basePage.setData(umsSubDeviceInfoQueryResponseDtoList);

        return basePage;

    }

    @Override
    public List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGroupId(UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto) {
        log.info("==========查询当前分组下挂载的子设备信息接口入参:{}", requestDto);
        if (!check()) {
            return null;
        }
        if (!checkDevice()) {
            throw new NewMediaException(DeviceErrorEnum.GET_NM_GROUP_ERROR);
        }
        String groupId = requestDto.getGroupId();
        if (StringUtils.isEmpty(groupId)) {
            return null;
        }
        List<NMDevice> list = NewMediaDeviceCache.getInstance().getDeviceByGroupId(groupId);
        if (CollectionUtil.isEmpty(list)) {
            log.error("查询当前分组下挂载的子设备为空");
            return null;
        }
        List<UmsSubDeviceInfoQueryResponseDto> collect = list.stream().map(a -> convert.convertToPage(a)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGbIds(UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto) {
        List<String> gbIds = requestDto.getGbIds();
        log.info("根据国标id查询设备信息入参 : [{}]", gbIds);
        if (!check()) {
            return null;
        }
        if (!checkDevice()) {
            throw new NewMediaException(DeviceErrorEnum.GET_NM_GROUP_ERROR);
        }
        List<NMDevice> list = NewMediaDeviceCache.getInstance().getDeviceByGroupId(gbIds);
        if (CollectionUtil.isEmpty(list)) {
            log.error("根据国标id查询设备信息为空");
            return null;
        }
        log.info("根据国标id查询设备信息 : [{}]", list);
        List<UmsSubDeviceInfoQueryResponseDto> collect = list.stream().map(a -> convert.convertToPage(a)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<UmsScheduleGroupItemQueryResponseDto> selectUmsGroupList(UmsScheduleGroupQueryRequestDto requestDto) {
        log.info("==========查询统一平台分组集合信息接口入参：{}", requestDto);
        if (!check()) {
            return null;
        }
        List<NmGroup> list = NewMediaDeviceCache.getInstance().getAllGroups();
        List<UmsScheduleGroupItemQueryResponseDto> collect = list.stream().map(a -> convert.convertToGroup(a)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<SelectChildUmsGroupResponseDto> selectChildUmsGroupList(SelectChildUmsGroupRequestDto requestDto) {
        log.info("根据当前分组ID查询子分组集合入参 : [{}]", requestDto);
        if (!check()) {
            return null;
        }
        String groupId = requestDto.getGroupId();
        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isBlank(groupId)) {
            groupId = "0";
        }
        NmGroup group = NewMediaDeviceCache.getInstance().getPGroupById(groupId);
        List<NmGroup> list = group.getSortChildGroups();
        if (CollectionUtil.isEmpty(list)) {
            log.error("根据当前分组ID查询子分组集合信息为空");
            return null;
        }
        List<SelectChildUmsGroupResponseDto> collect = list.stream().map(a -> convert.convertToSelectGroup(a)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Boolean sendOrderData(SVROrderDTO dto) {
        log.info("=============发送SVR宏指令接口入参：{}",dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        NewMediaEntity entity = mapper.selectById(dto.getUmsId());
        if (entity == null) {
            throw new NewMediaException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if (!check()) {
            return false;
        }
        NewMediaBasicParam param = getParam(entity.getSsid());
        String string = null;
        try {
            string = template.postForObject(param.getUrl() + "/svrorder/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        } catch (RestClientException e) {
            log.error("====发送SVR宏指令失败{}",e);
            return false;
        }
        log.info("发送SVR宏指令中间件应答:{}", string);
        NewMediaResponse response = JSON.parseObject(string, NewMediaResponse.class);
        String errorMsg = "发送SVR宏指令失败:{},{},{}";
        responseUtil.handleNewMediaRes(errorMsg,DeviceErrorEnum.NM_SEND_ORDER_FAILED,response);
        return true;
    }

    @Override
    public GetAudioCapVO getAudioCap(GetAudioCapDTO getAudioCapDTO) {
        log.info("====获取音频能力集接口入参：{}",getAudioCapDTO);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        NewMediaEntity entity = mapper.selectById(Integer.valueOf(getAudioCapDTO.getUmsId()));
        if (entity == null) {
            throw new NewMediaException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if (!check()) {
            return null;
        }
        AudioCapDTO dto = new AudioCapDTO();
        dto.setDeviceId(getAudioCapDTO.getDeviceID());
        NewMediaBasicParam param = getParam(entity.getSsid());
        String s = null;
        try {
            s = template.postForObject(param.getUrl() + "/svraudiocap/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        } catch (RestClientException e) {
            log.error("====获取音频能力集失败{}",e);
            return null;
        }
        log.info("发送SVR宏指令中间件应答:{}", s);
        NewMediaResponse response = JSON.parseObject(s, NewMediaResponse.class);
        String errorMsg = "获取音频能力集失败:{},{},{}";
        responseUtil.handleNewMediaRes(errorMsg,DeviceErrorEnum.NM_GET_AUDIO_CAP_FAILED,response);
        GetAudioResponse audioResponse = JSONObject.parseObject(s, GetAudioResponse.class);
        return audioResponse.getAudioCap();
    }

    @Override
    public GetSvrAudioActStateVo getSvrAudioActState(GetSvrAudioActStateDTO dto) {
        log.info("=========获取SVR当前语音激励状态接口入参：{}",dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        NewMediaEntity entity = mapper.selectById(Integer.valueOf(dto.getUmsId()));
        if (entity == null) {
            throw new NewMediaException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if (!check()) {
            return null;
        }
        NewMediaBasicParam param = getParam(entity.getSsid());
        String s = null;
        try {
            s = template.postForObject(param.getUrl() + "/svraudioactcfg/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        } catch (RestClientException e) {
            log.error("====获取SVR当前语音激励状态失败{}",e);
            return null;
        }
        log.info("获取SVR当前语音激励状态中间件应答:{}", s);
        NewMediaResponse response = JSON.parseObject(s, NewMediaResponse.class);
        String errorMsg = "获取SVR当前语音激励状态失败:{},{},{}";
        responseUtil.handleNewMediaRes(errorMsg,DeviceErrorEnum.NM_GET_AUDIO_STATE_FAILED,response);
        GetSvrAudioActStateVo vo = JSONObject.parseObject(s, GetSvrAudioActStateVo.class);
        return vo;
    }

    @Override
    public GetBurnStateVO getBurnState(GetBurnStateDTO dto) {
        log.info("=========获取SVR当前刻录状态接口入参：{}",dto);
        RestTemplate template = remoteRestTemplate.getRestTemplate();
        NewMediaEntity entity = mapper.selectById(Integer.valueOf(dto.getUmsId()));
        if (entity == null) {
            throw new NewMediaException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if (!check()) {
            return null;
        }
        NewMediaBasicParam param = getParam(entity.getSsid());
        String s = null;
        try {
            s = template.postForObject(param.getUrl() + "/svrburnstate/{ssid}/{ssno}", JSON.toJSONString(dto), String.class, param.getParamMap());
        } catch (RestClientException e) {
            log.error("====获取SVR当前刻录状态失败{}",e);
            return null;
        }
        log.info("获取SVR当前刻录状态中间件应答:{}", s);
        NewMediaResponse response = JSON.parseObject(s, NewMediaResponse.class);
        String errorMsg = "获取SVR当前刻录状态失败:{},{},{}";
        responseUtil.handleNewMediaRes(errorMsg,DeviceErrorEnum.NM_GET_BURN_STATE_FAILED,response);
        GetBurnStateResponse res = JSONObject.parseObject(s, GetBurnStateResponse.class);
        BurnInfo info = res.getBurnInfo();
        GetBurnStateVO vo = new GetBurnStateVO();
        vo.setChnId(info.getBurnChnId());
        vo.setMsgType(info.getType());
        vo.setStatus(info.getBurnState());
        vo.setTotalSpace(info.getTotalSpace());
        vo.setRemainingSpace(info.getFreeSpace());
        vo.setErrorCode(info.getErrorCode());
        vo.setDvdId(info.getDvdId());
        return vo;
    }

    private boolean check() {
        if (newMediaStatusPoll.get(1) == null) {
            log.error("=======新媒体处于离线状态无法获取设备信息");
            return false;
        } else {
            return true;
        }
    }

}
