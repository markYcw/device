package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Gps信息
 * 
 * @author ycw
 * 
 */
@Data
public class Gps {
	/**
	 * Gps号
	 */
	@ApiModelProperty("视频源通道号")
	private int sn;
	/**
	 * 产生的时间
	 */
	@ApiModelProperty("时间")
	private String time;
	/**
	 * 经度
	 */
	@ApiModelProperty("经度")
	private String longitude;
	/**
	 * 纬度
	 */
	@ApiModelProperty("纬度")
	private String latitude;
	/**
	 * 纠偏后的经度
	 */
	@ApiModelProperty("纠偏后的经度")
	private String marLongitude;
	/**
	 * 纠偏后的纬度
	 */
	@ApiModelProperty("纠偏后的纬度")
	private String marLatitude;
	/**
	 * 移动设备的速度
	 */
	@ApiModelProperty("移动设备的速度")
	private String speed;
	
	/**
	 * 设备的puId
	 */
	@ApiModelProperty("设备的puId")
   private String puId;


}
