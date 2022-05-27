package com.kedacom.device.core.notify.nm.pojo;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 获取设备组信息
 * 
 * @author ycw
 * 
 */
@Data
public class AlarmInfo extends DeviceNotifyRequestDTO {


	@ApiModelProperty("告警状态1:告警 0:消警")
	private Integer AlarmState;

	@ApiModelProperty("告警类型 0：无效告警类型 1：刻录出错 2：磁盘错(暂不检测) 3：无硬盘 4：IP地址冲突 5：网络断开 6：网速下降" +
			"7：前端掉线 8：CPU过载 9：内存不足 10：前端视频源丢失 11：并口输入(合成通道并口输入3作为虚拟并口告警，实为开启刻录)" +
			"12：主动IPC发送能力不足 13：MP4分区空间将满（默认剩余空间少于50G，可配置） 14：MP4分区剩余空间不足（默认剩余空间少于10G，可配置）" +
			"15：多媒体主机断链 (新增加：适用2910、2720) 16：光盘剩余空间不足（具体见刻录参数配置） 17：电池电量低（默认电量少于10%，不可配）" +
			"18：红外学习成功告警 19：无音频接入 20：系统时间错误告警 21：移动侦测 22：审讯控制信号告警 23：合成通道无码流(未编码)" +
			"24：失焦 25：亮度变化 26：场景变换 27：人员聚集 28：前端并口输入 29：人脸马赛克检测 30：刻录异常停止 31：音量过高 32：音量过低" +
			"33：音量过低，区别于SVR_ALARM_TYPE_NO_AUDIO 34：磁盘丢失 35：raid失效 36：视频算法告警 37：电源掉电告警 38：录制磁盘满告警 39：录制磁盘出错告警" +
			"40：录像异常告警")
	private Integer AlarmType;

	@ApiModelProperty("告警通道id")
	private Integer AlarmChnId;

	@ApiModelProperty("告警通道子id")
	private Integer AlarmChnSubId;

	@ApiModelProperty("告警描述")
	private String AlarmDesc;

}
