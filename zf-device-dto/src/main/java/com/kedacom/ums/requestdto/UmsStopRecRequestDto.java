package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "停止录像请求参数类")
public class UmsStopRecRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "成员设备Id不能为空")
    @ApiModelProperty(value = "成员设备Id")
    private String deviceId;

    @NotBlank(message = "录像Id不能为空")
    @ApiModelProperty(value = "录像Id")
    private String recId;

}
