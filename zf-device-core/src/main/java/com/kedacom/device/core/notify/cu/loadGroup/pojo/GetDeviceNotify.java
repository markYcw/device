package com.kedacom.device.core.notify.cu.loadGroup.pojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 8.2 获取设备信息
 * 
 * @author ycw
 * 
 */
@Data
public class GetDeviceNotify  {

	@ApiModelProperty("ssid")
	private Integer ssid;

	@ApiModelProperty("监控设备")
	private List<PDevice> deviceList;
	

	@ApiModelProperty("是否传完")
	private Integer isEnd;

}
