package com.kedacom.deviceListener.notify.cu;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/29 10:10
 * @description
 */
@Data
@ApiModel(description = "监控平台异常告警通知")
public class CuAlarmDTO extends DeviceNotifyRequestDTO {

    @ApiModelProperty("1-产生告警，2-恢复告警")
    private Integer status;

    @ApiModelProperty("告警产生或者恢复的时间")
    private Integer time;

    @ApiModelProperty("告警类型 1：移动侦测 2：告警输入 3：磁盘满 4：视频源丢失")
    private Integer type;

    @ApiModelProperty("告警通道")
    private Integer chn;

    @ApiModelProperty("纠偏后的经度")
    private List<SrcChsVo> srcChs;

    @ApiModelProperty("设备的puId")
    private String puId;

}
