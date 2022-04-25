package com.kedacom.device.core.notify.nm.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分组设备ID对应关系
 * @author ycw
 */
@Data
public class NmGroupDeviceIds implements Cloneable{


	@ApiModelProperty("分组id")
	private String groupId;

	@ApiModelProperty("设备id")
	private String id;

	@ApiModelProperty("设备国标id")
	private String gbId;

}
