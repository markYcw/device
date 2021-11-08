package com.kedacom.device.core.notify.cu.loadGroup.pojo;

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
public class GetGroupNotify {

	/**
	 * 分组
	 */
	@ApiModelProperty("分组")
	private List<PGroup> groupList = new ArrayList<PGroup>();
	/**
	 * 是否传完
	 */
	@ApiModelProperty("是否传完")
	private Integer isEnd;

	/**
	 * 会话标识
	 */
	@ApiModelProperty("会话标识")
	private Integer ssid;


}
