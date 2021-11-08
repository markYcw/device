package com.kedacom.device.core.notify.cu.loadGroup;


import com.kedacom.device.core.notify.cu.loadGroup.pojo.Cu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Hashtable;

/**
 * 用于加载监控平台分组
 * @author ycw
 * @see #getSessionManager()
 *
 */
@Slf4j
@Component
public class CuClient {

	/**
	 * 会话管理。根据目前的设计，一个终端最多一个会话，好比一个监控平台只有一个主链。
	 */
	private CuSessionManager sessionManager;
	
	/**
	 * “会话平台”集合。
	 * key：监控平台标识， value:监控平台详细信息
	 */
	private Hashtable<Integer, Cu> cuCacheByID = new Hashtable<Integer, Cu>();

	/**
	 * Cu设备加载线程
	 */
	private CuDeviceLoadThread deviceLoadThread;


	public CuClient(){

		this.sessionManager = new CuSessionManager(this);

		deviceLoadThread = new CuDeviceLoadThread(this);

	}

	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public CuSessionManager getSessionManager() {
		return sessionManager;
	}

	
	private boolean checkCu(Cu cu){
		if(cu.getId() <= 0){
			throw new RuntimeException("必须监控平台ID");
		}
		if(cu.getIp() == null || cu.getIp().trim().length() <= 0){
			throw new RuntimeException("必须指定监控平台IP");
		}
		return true;
	}

	/**
	 * 获取指定的平台信息。
	 * @param id 平台ID
	 * @return
	 */
	public Cu getCuByID(int id){
		return this.cuCacheByID.get(id);
	}
	

	private int getSSIDByID(int id){
		return sessionManager.getSSIDByCuID(id);
	}
	
	
}
