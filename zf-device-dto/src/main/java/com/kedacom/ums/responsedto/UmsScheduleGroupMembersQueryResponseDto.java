package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel
public class UmsScheduleGroupMembersQueryResponseDto implements Serializable {

    @ApiModelProperty(value = "成员设备类型")
    private String DeviceType;

    @ApiModelProperty(value = "成员设备Id")
    private String DeviceID;

    @ApiModelProperty(value = "设备状态")
    private Integer Status;

}
