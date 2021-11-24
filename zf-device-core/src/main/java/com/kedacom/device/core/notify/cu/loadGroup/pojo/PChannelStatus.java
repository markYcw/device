package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频源通道状态
 * 
 * @author dengjie
 * 
 */
@Data
public class PChannelStatus {
	/**
	 * 视频源通道号
	 */
	private int sn;
	
	/**
	 * 通道名称
	 */
	private String name;
	
	/**
	 * 是否启用。true启用，false信用，null状态未变化
	 */
	private Integer enable;
	/**
	 * 是否在线。true上线,false下线，null状态未变化
	 */
	@ApiModelProperty("在线状态 0：离线 1：在线")
	private Integer online;

	/**
	 * 是否平台录像。true正在平台录像,false不在平台录像，null状态未变化
	 */
	private Integer platRecord;
	
	/**
	 * 是否前端录像。true正在前端录像,false不在前端录像，null状态未变化
	 */
	private Integer puRecord;


}
