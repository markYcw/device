package com.kedacom.device.core.notify.cu.loadGroup;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.dto.DevicesDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.pojo.Subscribe;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.*;
import com.kedacom.device.core.service.CuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 监控设备加载线程
 * @author ycw
 *
 */
@Slf4j
@Component
public class CuDeviceLoadThread {

	@Autowired
	private CuService cuService;

	@Autowired
	private CuMapper cuMapper;

	
	/**
	 * 会话标识
	 */
	private LinkedList<Integer> ssidCache =  new LinkedList<Integer>();
	
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
	
	private static int serial = 0;
	private boolean work = true;
	private Object lock = new Object();
	private Thread loadThread;
	private CuClient client;


	public CuDeviceLoadThread(CuClient client){
		this.client = client;
	}

	/**
	 * 返回全局唯一的监控平台客户端
	 */
	public CuClient getCuClient(){
		if(!ObjectUtils.isEmpty(client)){
			return this.client;
		}else {
			return null;
		}
	}


	/**
	 * 加载下一个监控平台的设备
	 */
	private void loadNextCu(){
		log.debug("curr ssid=" + currSsid);
		if(currSsid != CuSession.INVALID_SSID){
			//当前正有加载currSsid的分组
			log.debug("loadNextCu() currSsid != CuSession.INVALID_SSID return");
			return;
		}

		//加载下一个监控平台的分组
		int nextSsid = CuSession.INVALID_SSID;
		synchronized (ssidCache){
			if(ssidCache.size() > 0){
				nextSsid = ssidCache.pollFirst();
			}
		}
		log.debug("next ssid=" + nextSsid);
		if(nextSsid >= 0){
			this.currSsid = nextSsid;
			try {
				this.requestLoadDeviceGroup(nextSsid);
			} catch (Exception e) {
				log.error("加载指定平台的设备分组失败, ssid=" + nextSsid, e);
				this.loadNextCu(); //忽略失败的，继续加载下一个
			}
		}
	
	}
	
	/**
	 * 指定监控平台设备加载完成
	 * @param ssid
	 * @param success
	 */
	private void onLoadComplete(int ssid, boolean success){
		log.debug("onLoadComplete ssid=" + ssid + ", success=" + success);
		
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		if(success){
			//成功
			unKownDeviceGroup.remove(ssid);
			session.getDeviceCache().setLoadComplete(true);
			this.onDeviceLoadCompate(ssid);
		}else{
			//失败。将ssid加进去，等待重新加载
			synchronized (ssidCache){
				ssidCache.addLast(ssid);
			}
			session.getDeviceCache().setLoadComplete(false);
		}

		if(this.currSsid == ssid){
			this.currSsid = CuSession.INVALID_SSID;
		}
		
		//继续加载下一个平台的设备
		this.loadNextCu();
	}
	
	/**
	 * 加载指定监控平台下一个分组的设备
	 * @param ssid
	 */
	private void loadNextDeviceGroup(int ssid){

		String groupId = null;
		LinkedList<String> list = unKownDeviceGroup.get(ssid);
		if(list != null){
			synchronized (list) {
				if(list.size() > 0){
					groupId = list.poll();
				}
			}
		}
		
		if(groupId != null){
			try {
				this.requestLoadDevice(ssid, groupId);
			} catch (Exception e) {
				log.debug("获取设备失败 ssid=" + ssid + ", groupId=" + groupId, e);
				loadNextDeviceGroup(ssid);//忽略失败的分组，继续加载下一个分组
			}
		}else{
			//已加载完成
			this.onLoadComplete(ssid, true);
		}
	}
	
	/**
	 * 加载分组。分组以“通知（Notify）”的方式上报。
	 * @throws Exception
	 */
	private void requestLoadDeviceGroup(int ssid) throws Exception{

		int cuId = client.getSessionManager().getCuIDBySSID(ssid);
	//	client.getCuOperate().startLoadDeviceGroup(cuId);
	}
	
	/**
	 * 加载指定分组的设备。设备以“通知（Notify）”的方式上报。
	 * @param ssid
	 * @param groupId 分组ID
	 * @throws Exception
	 */
	private void requestLoadDevice(int ssid, String groupId) throws Exception{
		//获取cu数据库ID
		LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CuEntity::getSsid,ssid);
		List<CuEntity> list = cuMapper.selectList(wrapper);
		CuEntity cuEntity = list.get(DevTypeConstant.getZero);

		DevicesDto devicesDto = new DevicesDto();
		Subscribe subscribe = new Subscribe();
		subscribe.setOnline(1);
		subscribe.setAlarm(0);
		subscribe.setChn(1);
		subscribe.setGps(0);
		subscribe.setTvwall(0);
		subscribe.setRec(1);
		subscribe.setTransdata(0);
		devicesDto.setSubscribe(subscribe);
		devicesDto.setDbId(cuEntity.getId());
		devicesDto.setGroupId(groupId);
		cuService.devices(devicesDto);
	}

	//==========================以上是业务逻辑=============================================
	//==========================以下是数据处理=============================================

	public void onNotify(INotify notify) {
		int ssid = notify.getSsid();
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		int id = 0;
		if(session != null){
			id = session.getCu().getId();
		}else{
			log.warn("无效的会话,ssid=" + ssid);
			return;
		}
		
		if(id <= 0){
			log.warn("会话中无有效的监控平台信息,ssid=" + ssid);
			return;
		}
		
		if (notify instanceof GetGroupNotify) {
			//获取分组
			log.info("加载分组信息通知");
			this.onDeviceGroupNotify((GetGroupNotify)notify);
		} else if (notify instanceof GetDeviceNotify) {
			//获取设备
			log.info("加载设备通知，本次加载"+((GetDeviceNotify) notify).getDeviceList().size()+"个设备");
			this.onDeviceNotify((GetDeviceNotify)notify);
		}else if (notify instanceof GetDeviceStatusNotify) {
			//设备状态
			log.info("加载设备ID为"+((GetDeviceStatusNotify) notify).getPuId()+"的设备状态");
			this.onDeviceStatus((GetDeviceStatusNotify)notify);
		}
	}

	private LinkedList<String> touchGroupList(int ssid){
		LinkedList<String> list = this.unKownDeviceGroup.get(ssid);
		if(list == null){
			list = new LinkedList<String>();
			this.unKownDeviceGroup.put(ssid, list);
		}
		return list;
	}
	private void addDeviceGroups(int ssid, Collection<PGroup> groups){
		LinkedList<String> list = this.touchGroupList(ssid);
		for(PGroup g : groups){
			synchronized (list) {
				list.push(g.getId());
			}
		}
	}

	/**
	 * 收到通知：分组
	 * @param notify
	 */
	public void onDeviceGroupNotify(GetGroupNotify notify){
		
		int ssid = notify.getSsid();
		Integer isEnd = notify.getIsEnd();
		List<PGroup> groups = notify.getGroupList();

		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		session.getDeviceCache().addDeviceGroups(groups);
		
		this.addDeviceGroups(ssid, groups);
		
		if(isEnd==1){
			//分组完成，开始获取设备
			this.loadNextDeviceGroup(ssid);
		}
	}
	
	/**
	 * 收到通知：设备
	 * @param notify
	 */
	public void onDeviceNotify(GetDeviceNotify notify){
		int ssid = notify.getSsid();
		Integer isSend = notify.getIsEnd();
		List<PDevice> devices = notify.getDeviceList();
		CuSession session = client.getSessionManager().getSessionBySSID(ssid);
		session.getDeviceCache().addDevices(devices);
		
		if(isSend==1){
			//一个分组下的设备获取完成，获取下一个分组的设备
			loadNextDeviceGroup(ssid);
		}
	}

	/**
	 * 收到设备状态
	 * @param notify
	 */
	public void onDeviceStatus(GetDeviceStatusNotify notify){
		int ssid = notify.getSsid();
		String puid = notify.getPuId();
		int type = notify.getType();
		switch (type) {
		case GetDeviceStatusNotify.TYPE_DEVICE_STATUS:
			//设备上下线
			Integer online = notify.getOnline();
			if(online != null){
				this.onDeviceStatus(ssid, puid, online);
			}
			break;

		/*case DeviceStatusNotify.TYPE_Channel:
			//视频源（通道）上下线
			List<PChannelStatus> status = notify.getChannelStatusList();
			this.onChannelStatus(ssid, puid, status);
			break;*/
			
		case GetDeviceStatusNotify.TYPE_ALARM:
			//报警（告警）
			//TODO 以前从来没用过，暂时不支持
			break;
		case GetDeviceStatusNotify.TYPE_REC:
			//录像状态
			break;

		case GetDeviceStatusNotify.TYPE_GPS:
			//GPS
			Gps gps = notify.getGps();
			ArrayList<Gps> gpsList = new ArrayList<>();
			if(gpsList != null && gpsList.size() > 0){
				this.onGps(ssid, puid, gpsList);
			}
			break;

		case GetDeviceStatusNotify.TYPE_DEVICE_IN:
			//设备入网
			break;

		case GetDeviceStatusNotify.TYPE_DEVICE_OUT:
			//设备退网
			this.onDeviceOut(ssid, puid);
			break;

		case GetDeviceStatusNotify.TYPE_DEVICE_UPDATE:
			//设备更新
			this.onDeviceUpdate(ssid, puid);
			break;

		default:
			break;
		}
	}


	//设备状态
	private void onDeviceStatus(int ssid, String puid, Integer online){
		if(online == null){
			return;
		}
		
		CuDeviceCache deviceCache = client.getSessionManager().getSessionBySSID(ssid).getDeviceCache();
		deviceCache.updateDeviceStatus(puid, online);
		
	/*	for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onDeviceStatus(puid, online);
			}catch(Exception e){
				log.warn("onGps() error", e);
			}
		}*/
	}
	
	//设备通道状态
	private void onChannelStatus(int ssid, String puid, List<PChannelStatus> status){
		if(status == null || status.size() <= 0){
			return;
		}
		CuDeviceCache deviceCache = client.getSessionManager().getSessionBySSID(ssid).getDeviceCache();
		deviceCache.updateChannelStatus(puid, status);

	/*	for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onChannelStatus(puid, status);
			}catch(Exception e){
				log.warn("onGps() error", e);
			}
		}*/
	}
	
	//GPS
	private void onGps(int ssid, String puid, List<Gps> gpsList){
		if(gpsList == null || gpsList.size() <= 0){
			return;
		}
		
	/*	for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onGps(puid, gpsList);
			}catch(Exception e){
				log.warn("onGps() error", e);
			}
		}*/
	}
	
	//设备入网
	@SuppressWarnings("deprecation")
	private void onDeviceIN(int ssid, PDevice device){
		log.info("===> onDeviceIN ssid="+ssid+" device="+(device != null ? device.getName():null));
		
		if(device == null)
			return;

		CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
		CuDeviceCache deviceCache = cuSession.getDeviceCache();
		PDevice oldDevice = deviceCache.getDevice(device.getPuId());
		
		// 先删除老的设备信息
		if(oldDevice != null){
			deviceCache.removeDevice(oldDevice.getPuId());
			
		/*	for (CuNotifyListener l : client.getAllListeners()) {
				try{
					l.onDeviceOut(oldDevice.getPuid());// 兼容老的业务系统
					l.onDeviceOut(cuSession.getCu().getId(), oldDevice.getPuid());
				}catch(Exception e){
					log.warn("onDeviceOut() error", e);
				}
			}*/
		}
		
		// 添加新入会设备信息
		deviceCache.addDevice(device);
/*		for (CuNotifyListener l : client.getAllListeners()) {
			try{
				l.onDeviceIn(cuSession.getCu().getId(),device);
			}catch(Exception e){
				log.warn("onDeviceIn() error", e);
			}
		}*/
	}

	//设备退网
	@SuppressWarnings("deprecation")
	private void onDeviceOut(int ssid ,String puid){
		log.info("===> onDeviceOut ssid="+ssid+" puid="+puid);
		
		if(ToolsUtil.isEmpty(puid))
			return;
		
		CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
		CuDeviceCache deviceCache = cuSession.getDeviceCache();
		PDevice device = deviceCache.getDevice(puid);
		if(device != null){
			deviceCache.removeDevice(puid);
			
		/*	for (CuNotifyListener l : client.getAllListeners()) {
				try{
					l.onDeviceOut(puid);// 兼容老的业务系统
					l.onDeviceOut(cuSession.getCu().getId(), puid);
				}catch(Exception e){
					log.warn("onDeviceOut() error", e);
				}
			}*/
			
		}
	}
	
	//设备更新
	private void onDeviceUpdate(int ssid ,String puid){
		//TODO 设备更新只能根据设备所在分组加载分组下全部设备，但现在无法确定设备所在分组，所以暂时无法处理。
//		CuDeviceCache deviceCache = client.getSessionManager().getSessionBySsid(ssid).getDeviceCache();
//		PDevice device = deviceCache.getDevice(puid);
//		if(device != null){
//			
//		}
	}
	
	//设备加载完成
	private void onDeviceLoadCompate(Integer ssid){
		LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CuEntity::getSsid,ssid);
		List<CuEntity> cuEntities = cuMapper.selectList(wrapper);
		CuEntity cuEntity = cuEntities.get(DevTypeConstant.getZero);
		log.info("============================监控平台IP为{}登录完成",cuEntity.getIp());
	}


	private void onRec(int ssid, String deviceId, Map<Integer, PChannelStatus> pChannelStatusMap) {
		log.info("设备通道状态变更通知:ssid=" + ssid + ",puId=" + deviceId + ",pChannelStatusMap=" + pChannelStatusMap);
		CuSession cuSession = client.getSessionManager().getSessionBySSID(ssid);
		CuDeviceCache deviceCache = cuSession.getDeviceCache();
		PDevice device = deviceCache.getDevice(deviceId);
		if (device == null) {
			return;
		}
		for (Map.Entry<Integer, PChannelStatus> entry : pChannelStatusMap.entrySet()) {
			Integer snno = entry.getKey();
			PChannelStatus channelStatus = entry.getValue();
			PChannel channel = device.getChannel(snno);
			if (channel == null || channelStatus == null) {
				continue;
			}
			if (channelStatus.getPlatRecord() != null) {
				channel.setPlatRecord(channelStatus.getPlatRecord());
			}

			if (channelStatus.getPuRecord() != null) {
				channel.setPuRecord(channelStatus.getPuRecord());
			}
		}

	}

}
