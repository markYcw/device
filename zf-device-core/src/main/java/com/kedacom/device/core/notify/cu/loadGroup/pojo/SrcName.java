package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/13 9:32
 * @description 视频源名称修改通知
 */
@Data
public class SrcName {

    @ApiModelProperty("设备名称")
    private String devName;

    @ApiModelProperty("视频源编号")
    private Integer chnSn;

    @ApiModelProperty("视频源名称")
    private String chnName;

}
