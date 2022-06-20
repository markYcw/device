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
public class NmGroupModifyNotify {


	@ApiModelProperty("分组")
	private List<NmGroup> groupList = new ArrayList<NmGroup>();


	@ApiModelProperty("是否传完 0否 1是")
	private Integer isEnd;


	@ApiModelProperty("操作时间")
	private Double operateTime;


	@ApiModelProperty("会话标识")
	private Integer ssid;


	@ApiModelProperty("操作类型 10:分组增加修改，11:分组删除")
	private Integer operateType;


}
