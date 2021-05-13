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
@ApiModel(value = "设置调度组成员设备静音请求参数类")
public class UmsScheduleGroupMembersSetSilenceRequestDto implements Serializable {

    @NotBlank(message = "成员设备类型不能为空")
    @ApiModelProperty(value = "成员设备类型")
    private String deviceType;

    @NotBlank(message = "成员设备Id不能为空")
    @ApiModelProperty(value = "成员设备Id")
    private String deviceId;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态（1:静音 0:不静音）")
    private Integer silenceState;

}
