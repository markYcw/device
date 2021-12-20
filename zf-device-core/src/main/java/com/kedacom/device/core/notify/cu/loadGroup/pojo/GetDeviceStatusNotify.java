package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 8.3设备状态变更通道
 * 
 * @author ycw
 * 
 */
@Data
public class GetDeviceStatusNotify {

	/**状态变更类型：设备上下线*/
	public static final int TYPE_DEVICE_STATUS = 0;
	/**状态变更类型：告警*/
	public static final int TYPE_ALARM = 1;
	/**状态变更类型：视频源*/
	public static final int TYPE_Channel = 2;
	/**状态变更类型：gps信息*/
	public static final int TYPE_GPS = 3;
	/**状态变更类型：录像状态*/
	public static final int TYPE_REC = 4;
	/**状态变更类型：设备入网通知*/
	public static final int TYPE_DEVICE_IN = 14;
	/**状态变更类型：设备退网通知*/
	public static final int TYPE_DEVICE_OUT = 15;
	/**状态变更类型：设备修改通知*/
	public static final int TYPE_DEVICE_UPDATE = 16;
	/**状态变更类型：增加组*/
	public static final int TYPE_ADD_GROUP = 17;
	/**状态变更类型：删除组*/
	public static final int TYPE_DEL_GROUP = 18;
	/**状态变更类型：视频源别名改变*/
	public static final int TYPE_UPDATE_CHANNEL_NAME = 19;

	@ApiModelProperty("ssid")
	private Integer ssid;

	@ApiModelProperty("流水号")
	private Integer ssno;

	@ApiModelProperty("在线 0, 报警1, 视频源通道2,gps 3,录像状态4,收到透明数据5, 电视墙新增10,电视墙删除11,电视墙修改12,电视墙状态13,设备入网14,设备退网15,设备修改16,增加组17,删除组18,视频源别名改变19")
	private Integer stateType;

	@ApiModelProperty("设备ID")
	private String puId;

	@ApiModelProperty("设备是否在线 0:离线，1:在线")
	private Integer online;

	@ApiModelProperty("视频源列表")
	private List<SrcChns> srcChns;

	@ApiModelProperty("录像状态列表")
	private List<Rec> recs;

	@ApiModelProperty("电视墙列表")
	private List<Tvs> tvs;

	@ApiModelProperty("Gps信息")
	private Gps gps;

	@ApiModelProperty("告警信息 stateType为1时生效")
	private Alarm alarm;

	@ApiModelProperty("设备入网信息stateType为14时生效")
	private PDevice device;

	@ApiModelProperty("视频源名称修改stateType为19时生效")
	private SrcName srcChnName;



}
