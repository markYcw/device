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
@ApiModel(description =  "调度组成员设备请求参数类(DeviceID为设备国标id-DeviceType不填)")
public class UmsScheduleGroupMembersRequestDto implements Serializable {

    @ApiModelProperty(value = "成员设备类型")
    private String DeviceType;

    @NotBlank(message = "成员设备国标Id不能为空")
    @ApiModelProperty(value = "成员设备国标Id")
    private String DeviceID;

}
