package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "创建调度组请求参数类")
public class UmsScheduleGroupCreateRequestDto implements Serializable {

    @ApiModelProperty(value = "平台id")
    private String umsId;

    private List<UmsScheduleGroupMembersRequestDto> members;

}
