package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
@ApiModel(value = ",DeviceType不填")
public class VideoVo implements Serializable {

    @ApiModelProperty(value = "设备国标ID")
    private String DeviceID;

    @ApiModelProperty(value = "设备类型")
    private String DeviceType;

}
