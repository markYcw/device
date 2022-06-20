package com.kedacom.device.core.notify.nm.pojo;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
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
public class BurnInfo extends DeviceNotifyRequestDTO {


	@ApiModelProperty("消息类型 0:未知 1:刻录状态 2:刻录进度 3:刻录错误 4:刻录全部完成 5:追加刻录电子笔录完成 6:刻录全状态,此时所有成员均有效")
	private Integer type;

	@ApiModelProperty("刻录通道id")
	private Integer burnChnId;

	@ApiModelProperty("状态，当MsgType为1时有效 0:空闲状态， 1:正在刻录 2:刻录完成 3:正在停止刻录中 4:正在补刻/重新刻录状态中 5:正在追加刻录 6:正在刻录平台录像")
	private Integer burnState;

	@ApiModelProperty("光盘总的空间(M),蓝光最大支持50G,网络序")
	private Integer totalSpace;

	@ApiModelProperty("光盘剩余大小单位mb")
	private Integer freeSpace;

	@ApiModelProperty("错误码")
	private Integer errorCode;

	@ApiModelProperty("dvd编号")
	private Integer dvdId;

}
