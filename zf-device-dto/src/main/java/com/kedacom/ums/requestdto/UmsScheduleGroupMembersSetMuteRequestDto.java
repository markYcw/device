package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "设置调度组成员设备哑音请求参数类(DeviceID为设备国标id-DeviceType不填)")
public class UmsScheduleGroupMembersSetMuteRequestDto implements Serializable {

    @NotBlank(message = "成员设备类型不能为空")
    @ApiModelProperty(value = "成员设备类型")
    private String DeviceType;

    @NotBlank(message = "成员国标设备Id不能为空")
    @ApiModelProperty(value = "成员设备国标Id")
    private String DeviceID;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态（1:哑音 0:不哑音）")
    private Integer MuteState;

}
