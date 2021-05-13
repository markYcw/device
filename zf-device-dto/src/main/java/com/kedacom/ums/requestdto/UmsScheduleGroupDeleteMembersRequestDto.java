package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "删除调度组成员设备信息请求参数类")
public class UmsScheduleGroupDeleteMembersRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "设备数据库Id(一般来说为UMS平台Id)")
    private String umsId;

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String groupId;

    @NotEmpty(message = "删除的成员设备Id不能为空")
    @ApiModelProperty(value = "删除的成员设备ID集合")
    private List<String> members;

}
