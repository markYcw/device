package com.kedacom.device.core.notify.nm.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 获取设备组信息
 * 
 * @author ycw
 * 
 */
@Data
public class NmGroupDeviceChangeNotify {


	@ApiModelProperty("分组")
	private List<NmGroupDeviceIds> groupList = new ArrayList<NmGroupDeviceIds>();


	@ApiModelProperty("是否传完 0否 1是")
	private Integer isEnd;


	@ApiModelProperty("操作时间")
	private Double operateTime;


	@ApiModelProperty("会话标识")
	private Integer ssid;


	@ApiModelProperty("操作类型 7:设备与分组关系发生变化")
	private Integer operateType;


}
