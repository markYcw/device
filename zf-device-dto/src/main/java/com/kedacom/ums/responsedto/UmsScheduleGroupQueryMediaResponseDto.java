package com.kedacom.ums.responsedto;

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
@ApiModel(description =  "查询调度组成员媒体源响应参数类")
public class UmsScheduleGroupQueryMediaResponseDto implements Serializable {

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "媒体源列表包括音频和视频")
    private List<QueryMediaResponseDto> Members;

}
