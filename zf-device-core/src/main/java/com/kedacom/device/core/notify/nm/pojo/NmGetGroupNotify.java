package com.kedacom.device.core.notify.nm.pojo;

import com.kedacom.device.core.notify.cu.loadGroup.pojo.PGroup;
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
public class NmGetGroupNotify {

	/**
	 * 分组
	 */
	@ApiModelProperty("分组")
	private List<NmGroup> groupList = new ArrayList<NmGroup>();

	/**
	 * 是否传完
	 */
	@ApiModelProperty("是否传完 0否 1是")
	private Integer isEnd;

	/**
	 * 总数
	 */
	@ApiModelProperty("总数")
	private Integer count;

	/**
	 * 会话标识
	 */
	@ApiModelProperty("会话标识")
	private Integer ssid;


}
