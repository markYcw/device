package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 监控设备通道。监控平台1.0叫设备通道，监控平台2.0在1.0的基础上，通道不再体现掉辅流，因此通道又叫视频源。
 * @author TaoPeng
 * @see PGroup
 * @see PDevice
 * @see PChannel
 *
 */
@Data
public class PChannel {

	/**
	 * 设备编号
	 */
	@ApiModelProperty("设备编号")
	private String puid;
	/**
	 * 通道号
	 */
	@ApiModelProperty("通道号")
	private int sn;
	
	/**
	 * 视频源名称/通道名称
	 */
	@ApiModelProperty("视频源名称/通道名称")
	private String name;
	
	/**
	 * 是否启用
	 */
	@ApiModelProperty("是否启用")
	private boolean enable = true;
	
	/**
	 * 是否在线
	 */
	@ApiModelProperty("设备是否在线 0:离线，1:在线")
	private Integer online;


	/**
	 * 是否平台录像。true正在平台录像,false不在平台录像，
	 */
	@ApiModelProperty("是否平台录像。true正在平台录像,false不在平台录像，")
	private boolean platRecord;
	
	/**
	 * 是否前端录像。true正在前端录像,false不在前端录像，
	 */
	@ApiModelProperty("是否前端录像。true正在前端录像,false不在前端录像，")
	private boolean puRecord;
	
	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	/**
	 * 获取通道号。兼容监控平台1.0SDK的方法。
	 * @deprecated replace by {@link #getSn()}
	 * @return
	 */
	public int getChnid(){
		return this.getSn();
	}

	public boolean isPlatRecord() {
		return platRecord;
	}

	public void setPlatRecord(boolean platRecord) {
		this.platRecord = platRecord;
	}

	public boolean isPuRecord() {
		return puRecord;
	}

	public void setPuRecord(boolean puRecord) {
		this.puRecord = puRecord;
	}
	
}
