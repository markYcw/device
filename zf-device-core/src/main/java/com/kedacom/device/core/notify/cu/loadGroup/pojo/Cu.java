package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import com.kedacom.device.core.notify.stragegy.DeviceType;
import lombok.Data;


/**
 * 监控平台
 * @author ycw
 *
 */
@Data
public class Cu {

	public static final int CU2 = DeviceType.CU2.toInt();
	
	private int version = CU2;
	/**
	 * 监控平台本地标识。比如：MT信息在本地数据库的数据ID
	 */
	private int id;

	/**
	 * 监控平台名称
	 */
	private String name;
	
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 连接端口。平台1.0端口默认1722,平台1.0端口默认80
	 */
	private int port = 80;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;

	public void update(Cu cu){
		this.setIp(cu.getIp());
		this.setPort(cu.getPort());
		this.setName(cu.getName());
		this.setUsername(cu.getUsername());
		this.setPassword(cu.getPassword());
		this.setVersion(cu.getVersion());
	}

}
