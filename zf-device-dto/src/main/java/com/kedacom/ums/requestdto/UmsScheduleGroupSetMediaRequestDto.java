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
@ApiModel(value = "设置调度组成员媒体源请求参数类")
public class UmsScheduleGroupSetMediaRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String groupId;

    @ApiModelProperty(value = "调度组成员设备类型")
    private List<UmsScheduleGroupMemberSetMediaRequestDto> members;

}
