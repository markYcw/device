package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Gps信息
 * 
 * @author ycw
 * 
 */
@Data
public class Alarm {

	@ApiModelProperty("1-产生告警，2-恢复告警")
	private Integer status;

	@ApiModelProperty("告警产生或者恢复的时间")
	private Integer time;

	@ApiModelProperty("告警类型 1：移动侦测 2：告警输入 3：磁盘满 4：视频源丢失")
	private Integer type;

	@ApiModelProperty("告警通道")
	private Integer chn;

	@ApiModelProperty("视频源信息")
	private List<SrcChns> srcChns;

	@ApiModelProperty("设备的puId")
    private String puId;


}
