package com.kedacom.device.core.notify.nm.pojo;

import com.kedacom.newMedia.pojo.NMDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 8.3设备状态变更通知
 * 
 * @author ycw
 * 
 */
@Data
public class NmDeviceStatusNotify {

	/**状态变更类型：设备上下线*/
	public static final int TYPE_DEVICE_STATUS = 1;

	/**状态变更类型：gps信息*/
	public static final int TYPE_GPS = 2;

	/**状态变更类型：设备入网通知*/
	public static final int TYPE_DEVICE_IN = 3;

	/**状态变更类型：设备修改通知*/
	public static final int TYPE_DEVICE_UPDATE = 4;

	/**状态变更类型：设备退网通知*/
	public static final int TYPE_DEVICE_OUT = 5;

	/**自动审核通过*/
	public static final int TYPE_OPERATE_OK = 6;

	/**表示设备与分组关系发生变化*/
	public static final int TYPE_OPERATE_CHANGE = 7;

	@ApiModelProperty("操作类型 1:状态更新，2:GPS, 3:新增create，4:修改update, 5:删除delete, 6:自动审核通过，7:表示设备与分组关系发生变化")
	private Integer operateType;

	@ApiModelProperty("操作时间")
	private Double operateTime;

	@ApiModelProperty("总数")
	private Integer total;

	@ApiModelProperty("设备列表")
	private List<NMDevice> devList;


}
