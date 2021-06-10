package com.kedacom.ums.responsedto;

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
@ApiModel(value = "查询讨论组响应参数类")
public class UmsScheduleGroupQueryDiscussionGroupResponseDto implements Serializable {

    @ApiModelProperty(value = "成员设备类型")
    private String DeviceType;

    @ApiModelProperty(value = "成员设备国标Id")
    private String DeviceID;

}
