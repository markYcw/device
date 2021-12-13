package com.kedacom.device.core.notify.cu.loadGroup;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.dto.DevicesDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.pojo.Subscribe;
import com.kedacom.device.core.convert.CuConvert;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.*;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.impl.CuServiceImpl;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.cu.*;
import com.kedacom.pojo.SystemWebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 监控设备加载线程
 *
 * @author ycw
 */
@Slf4j
@Component
public class CuDeviceLoadThread {

    @Autowired
    private CuService cuService;

    @Autowired
    private CuMapper cuMapper;

    @Autowired
    private WebsocketFeign websocketFeign;

    @Autowired
    private RegisterListenerService registerListenerService;

    @Autowired
    private DeviceNotifyUtils notifyUtils;

    @Autowired
    private CuConvert convert;


    /**
     * 当前正在加载的ssid
     */
    private int currSsid = CuSession.INVALID_SSID; //

    /**
     * 未加载设备的分组集合
     * key：ssid会话ID
     * value : 分组id
     */
    private Hashtable<Integer, LinkedList<String>> unKownDeviceGroup = new Hashtable<Integer, LinkedList<String>>();

    /**
     * 监控平台客户端
     */
    private CuClient client;


    public CuDeviceLoadThread(CuClient client) {
        this.client = client;
    }

    /**
     * 返回全局唯一的监控平台客户端
     */
    public CuClient getCuClient() {
        if (!ObjectUtils.isEmpty(client)) {
            return this.client;
        } else {
            return null;
        }
    }

    /**
     * 指定监控平台设备加载完成
     *
     * @param ssid
     * @param success
     */
    private void onLoadComplete(int ssid, boolean success) {
        log.debug("onLoadComplete ssid=" + ssid + ", success=" + success);

        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        if (success) {
            //成功
            unKownDeviceGroup.remove(ssid);
            session.getDeviceCache().setLoadComplete(true);
            this.onDeviceLoadCompate(ssid);
        }
    }

    /**
     * 加载指定监控平台下一个分组的设备
     *
     * @param ssid
     */
    private void loadNextDeviceGroup(int ssid) {

        String groupId = null;
        LinkedList<String> list = unKownDeviceGroup.get(ssid);
        if (list != null) {
            synchronized (list) {
                if (list.size() > 0) {
                    groupId = list.poll();
                }
            }
        }

        if (groupId != null) {
            try {
                this.requestLoadDevice(ssid, groupId);
            } catch (Exception e) {
                log.debug("获取设备失败 ssid=" + ssid + ", groupId=" + groupId, e);
                loadNextDeviceGroup(ssid);//忽略失败的分组，继续加载下一个分组
            }
        } else {
            //已加载完成
            this.onLoadComplete(ssid, true);
        }
    }

    /**
     * 加载指定分组的设备。设备以“通知（Notify）”的方式上报。
     *
     * @param ssid
     * @param groupId 分组ID
     * @throws Exception
     */
    private void requestLoadDevice(int ssid, String groupId) throws Exception {
        //获取cu数据库ID
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid, ssid);
        List<CuEntity> list = cuMapper.selectList(wrapper);
        CuEntity cuEntity = list.get(DevTypeConstant.getZero);

        DevicesDto devicesDto = new DevicesDto();
        Subscribe subscribe = new Subscribe();
        subscribe.setOnline(1);
        subscribe.setAlarm(1);
        subscribe.setChn(1);
        subscribe.setGps(0);
        subscribe.setTvwall(0);
        subscribe.setRec(1);
        subscribe.setTransdata(0);
        devicesDto.setSubscribe(subscribe);
        devicesDto.setKmId(cuEntity.getId());
        devicesDto.setGroupId(groupId);
        cuService.devices(devicesDto);
    }

    //==========================以上是业务逻辑=============================================
    //==========================以下是数据处理=============================================

    private LinkedList<String> touchGroupList(int ssid) {
        LinkedList<String> list = this.unKownDeviceGroup.get(ssid);
        if (list == null) {
            list = new LinkedList<String>();
            this.unKownDeviceGroup.put(ssid, list);
        }
        return list;
    }

    private void addDeviceGroups(int ssid, Collection<PGroup> groups) {
        LinkedList<String> list = this.touchGroupList(ssid);
        for (PGroup g : groups) {
            synchronized (list) {
                list.push(g.getId());
            }
        }
    }

    /**
     * 收到通知：分组
     *
     * @param notify
     */
    public void onDeviceGroupNotify(GetGroupNotify notify) {

        int ssid = notify.getSsid();
        Integer isEnd = notify.getIsEnd();
        List<PGroup> groups = notify.getGroupList();

        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        if (session != null) {
            session.getDeviceCache().addDeviceGroups(groups);

            this.addDeviceGroups(ssid, groups);

            if (isEnd == 1) {
                //分组完成，开始获取设备
                this.loadNextDeviceGroup(ssid);
            }
        }

    }

    /**+
     * 根据分组ID获取分组
     * @param ssid
     * @return
     */
    public PGroup getPGroupById(Integer ssid,String groupId){
        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        if (session != null) {
           return session.getDeviceCache().getPGroupById(groupId);
        }
        return null;
    }

    /**
     * 收到通知：设备
     *
     * @param notify
     */
    public void onDeviceNotify(GetDeviceNotify notify) {
        int ssid = notify.getSsid();
        Integer isSend = notify.getIsEnd();
        List<PDevice> devices = notify.getDeviceList();
        //给设备下每一个通道设置设备PuId
        devices.stream().forEach(pDevice -> setSnPuId(pDevice));
        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        //有可能当前分组底下未挂载设备但是子分组底下挂载设备所以对设备列表进行判空
        if (session != null) {
            session.getDeviceCache().addDevices(devices);

            if (isSend == 1) {
                //一个分组下的设备获取完成，获取下一个分组的设备
                loadNextDeviceGroup(ssid);
            }
        }
    }

	/**
	 * 给设备下每一个通道设置设备PuId
	 * @param pDevice
	 */
	public void setSnPuId(PDevice pDevice){
		List<SrcChn> srcChns = pDevice.getSrcChns();
		Iterator<SrcChn> iterator = srcChns.iterator();
		while (iterator.hasNext()){
			SrcChn next = iterator.next();
			next.setPuId(pDevice.getPuId());
		}
	}

    /**
     * 收到设备状态
     *
     * @param notify
     */
    public void onDeviceStatus(GetDeviceStatusNotify notify) {
        int ssid = notify.getSsid();
        String puid = notify.getPuId();
        int type = notify.getStateType();
        switch (type) {
            case GetDeviceStatusNotify.TYPE_DEVICE_STATUS:
                //设备上下线
                Integer online = notify.getOnline();
                if (online != null) {
                    this.onDeviceStatus(ssid, puid, online);
                }
                break;

            case GetDeviceStatusNotify.TYPE_Channel:
                //视频源（通道）上下线
				List<SrcChns> srcChns = notify.getSrcChns();
				this.onDeviceChnStatus(ssid, puid, srcChns);
                break;

            case GetDeviceStatusNotify.TYPE_ALARM:
                //报警（告警）收到报警通知以后给前端以及业务发通知内容
                this.onDeviceChnAlarm(notify);
                break;
            case GetDeviceStatusNotify.TYPE_REC:
                //录像状态
                List<Rec> recs = notify.getRecs();
                this.onDeviceChnRecStatus(ssid, puid, recs);
                break;

            case GetDeviceStatusNotify.TYPE_GPS:
                //GPS
                Gps gps = notify.getGps();
                ArrayList<Gps> gpsList = new ArrayList<>();
                if (gpsList != null && gpsList.size() > 0) {
                    this.onGps(ssid, puid, gpsList);
                }
                break;

            case GetDeviceStatusNotify.TYPE_DEVICE_IN:
                //设备入网
                this.onDeviceIN(ssid, notify.getDevice());
                break;

            case GetDeviceStatusNotify.TYPE_DEVICE_OUT:
                //设备退网
                this.onDeviceOut(ssid, puid);
                break;

            case GetDeviceStatusNotify.TYPE_DEVICE_UPDATE:
                //设备更新
                this.onDeviceUpdate(ssid, notify);
                break;

            default:
                break;
        }
    }

    /**
     * 获取数据库ID
     * @param ssid
     */
    private Integer getDbId(Integer ssid){
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid, ssid);
        List<CuEntity> cuEntities = cuMapper.selectList(wrapper);
        return cuEntities.get(DevTypeConstant.getZero).getId();
    }


    //设备状态
    private void onDeviceStatus(int ssid, String puid, Integer online) {
        if (online == null) {
            return;
        }
        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        if (session != null) {
            CuDeviceCache deviceCache = session.getDeviceCache();
            deviceCache.updateDeviceStatus(puid, online);
            //发送录像状态通知给业务方
            CuDeviceStateDTO dto = new CuDeviceStateDTO();
            dto.setMsgType(MsgType.CU_DEVICE_REC_STATE_NTY.getType());
            dto.setPuId(puid);
            dto.setOnline(online);
            dto.setDbId(getDbId(ssid));
            List<KmListenerEntity> all = registerListenerService.getAll(MsgType.CU_DEVICE_REC_STATE_NTY.getType());
            if (!CollectionUtil.isEmpty(all)) {
                for (KmListenerEntity kmListenerEntity : all) {
                    try {
                        notifyUtils.cuDeviceNty(kmListenerEntity.getUrl(), dto);
                    } catch (Exception e) {
                        log.error("------------CU发送录像状态通知给业务方失败", e);
                    }
                }
            }
        }
    }

    //设备通道状态
    private void onDeviceChnStatus(int ssid, String puid, List<SrcChns> srcChns) {
        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        if (session != null) {
            CuDeviceCache deviceCache = session.getDeviceCache();
            deviceCache.updateDeviceChnStatus(puid,srcChns);
            //发送设备通道状态通知给业务方
            CuChnStatusDTO dto = new CuChnStatusDTO();
            dto.setMsgType(MsgType.CU_CHN_STATE_NTY.getType());
            dto.setPuId(puid);
            List<SrcChsVo> collect = srcChns.stream().map(a -> convert.convertToSrcChsVo(a)).collect(Collectors.toList());
            dto.setSrcChsVos(collect);
            dto.setDbId(getDbId(ssid));
            List<KmListenerEntity> all = registerListenerService.getAll(MsgType.CU_CHN_STATE_NTY.getType());
            if (!CollectionUtil.isEmpty(all)) {
                for (KmListenerEntity kmListenerEntity : all) {
                    try {
                        notifyUtils.cuDeviceNty(kmListenerEntity.getUrl(), dto);
                    } catch (Exception e) {
                        log.error("------------CU发送录像状态通知给业务方失败", e);
                    }
                }
            }
        }
    }


    //设备告警通知
    private void onDeviceChnAlarm(GetDeviceStatusNotify notify) {
        Alarm alarm = notify.getAlarm();
        alarm.setPuId(notify.getPuId());
        CuAlarmDTO cuAlarmDTO = convert.convertToCuAlarmDTO(alarm);
        cuAlarmDTO.setMsgType(MsgType.CU_ALARM_NTY.getType());
        //发送webSocket给前端
        SystemWebSocketMessage message = new SystemWebSocketMessage();
        message.setOperationType(4);
        message.setServerName("device");
        message.setData(alarm);
        log.info("===============发送CU报警（告警）收到报警通知webSocket给前端{}", JSON.toJSONString(message));
        websocketFeign.sendInfo(JSON.toJSONString(message));
        List<KmListenerEntity> all = registerListenerService.getAll(MsgType.CU_ALARM_NTY.getType());
        if (!CollectionUtil.isEmpty(all)) {
            for (KmListenerEntity kmListenerEntity : all) {
                try {
                    notifyUtils.cuDeviceNty(kmListenerEntity.getUrl(), cuAlarmDTO);
                } catch (Exception e) {
                    log.error("------------CU发送报警（告警）收到报警通知给业务方失败", e);
                }
            }
        }
    }

    //设备录像状态
    private void onDeviceChnRecStatus(int ssid, String puid, List<Rec> recs) {
        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        if (session != null) {
            CuDeviceCache deviceCache = session.getDeviceCache();
            deviceCache.updateDeviceChnRecStatus(puid,recs);
            CuChnRecStatusDTO dto = new CuChnRecStatusDTO();
            dto.setPuId(puid);
            List<RecVo> collect = recs.stream().map(a -> convert.convertToRecVo(a)).collect(Collectors.toList());
            dto.setRecVos(collect);
            //推送设备录像状态给订阅者
            List<KmListenerEntity> all = registerListenerService.getAll(MsgType.CU_CHN_REC_STATE_NTY.getType());
            if (!CollectionUtil.isEmpty(all)) {
                for (KmListenerEntity kmListenerEntity : all) {
                    try {
                        notifyUtils.cuDeviceNty(kmListenerEntity.getUrl(), dto);
                    } catch (Exception e) {
                        log.error("------------CU发送设备录像状态通知给业务方失败", e);
                    }
                }
            }
        }
    }

    //GPS
    private void onGps(int ssid, String puid, List<Gps> gpsList) {
        if (gpsList == null || gpsList.size() <= 0) {
            return;
        }
    }

    //设备入网
    @SuppressWarnings("deprecation")
    private void onDeviceIN(int ssid, PDevice device) {
        log.info("===> onDeviceIN ssid=" + ssid + " device=" + (device != null ? device.getName() : null));

        if (device == null)
            return;

        CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
        CuDeviceCache deviceCache = cuSession.getDeviceCache();
        PDevice oldDevice = deviceCache.getDevice(device.getPuId());

        // 先删除老的设备信息
        if (oldDevice != null) {
            deviceCache.removeDevice(oldDevice.getPuId());

        }

        // 添加新入会设备信息
        deviceCache.addDevice(device);
    }

    //设备更新
    private void onDeviceUpdate(int ssid, GetDeviceStatusNotify notify) {
        log.info("===> onDeviceUpdate ssid={},Notify:{}",ssid,notify);
        SrcName srcName = notify.getSrcChnName();
        if (srcName == null)
            return;

        CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
        CuDeviceCache deviceCache = cuSession.getDeviceCache();
        PDevice oldDevice = deviceCache.getDevice(notify.getPuId());

        // 先删除老的设备信息
        if (oldDevice != null) {
            deviceCache.removeDevice(oldDevice.getPuId());

        }
        //设置新名称
        oldDevice.setName(srcName.getDevName());
        //设置视频源名称
        List<SrcChn> srcChns = oldDevice.getSrcChns();
        Iterator<SrcChn> iterator = srcChns.iterator();
        while (iterator.hasNext()){
            SrcChn next = iterator.next();
            if(next.getSn().equals(srcName.getChnSn())){
                next.setName(srcName.getChnName());
            }
        }
        // 添加新入会设备信息
        deviceCache.addDevice(oldDevice);

    }

    //设备退网
    @SuppressWarnings("deprecation")
    private void onDeviceOut(int ssid, String puid) {
        log.info("===> onDeviceOut ssid=" + ssid + " puid=" + puid);

        if (ToolsUtil.isEmpty(puid))
            return;

        CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
        CuDeviceCache deviceCache = cuSession.getDeviceCache();
        PDevice device = deviceCache.getDevice(puid);
        if (device != null) {
            deviceCache.removeDevice(puid);

        }
    }

    //设备加载完成
    private void onDeviceLoadCompate(Integer ssid) {
        CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
        CuDeviceCache deviceCache = cuSession.getDeviceCache();
        PGroup rootGroup = deviceCache.getPGroupById(deviceCache.getRootGroupId());
        CompletableFuture.runAsync(()->deviceCache.deviceCount(rootGroup));
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid, ssid);
        List<CuEntity> cuEntities = cuMapper.selectList(wrapper);
        CuEntity cuEntity = cuEntities.get(DevTypeConstant.getZero);
        log.info("============================监控平台IP为{}登录完成", cuEntity.getIp());
        CuServiceImpl.cuDeviceStatusPoll.put(cuEntity.getId(),DevTypeConstant.updateRecordKey);
    }

    /**
     * 更新设备在线总数分组中设备总数
     * @param ssid
     */
    public void updateDeviceStatusCount(Integer ssid){
        CuSession session = client.getSessionManager().getSessionBySSID(ssid);
        if(session!=null){
            CuDeviceCache deviceCache = session.getDeviceCache();
            PGroup pGroupById = deviceCache.getPGroupById(deviceCache.getRootGroupId());
            CompletableFuture.runAsync(()->deviceCache.deviceCount(pGroupById));
        }
    }


}
