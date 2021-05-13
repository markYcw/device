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
@ApiModel(value = "被呼叫的成员设备信息参数类")
public class UmsScheduleGroupSubDeviceCallMembersRequestDto implements Serializable {

    @ApiModelProperty("设备Id")
    @NotBlank(message = "设备Id不能为空")
    private String deviceId;

    @ApiModelProperty("主叫号码、席位号")
    private String localId;

}