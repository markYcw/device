package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "设置调度组静音请求参数类")
public class UmsScheduleGroupSetSilenceRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String groupId;

    @NotBlank(message = "调度组成员设备设置静音参数不能为空")
    @ApiModelProperty(value = "调度组成员设备设置静音")
    private List<UmsScheduleGroupMembersSetSilenceRequestDto> members;

}
