package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "查询调度组广播源响应参数类")
public class UmsScheduleGroupQueryBroadcastResponseDto implements Serializable {

    @ApiModelProperty(value = "调度组ID")
    private String GroupID;

    @ApiModelProperty(value = "广播源列表包括音频和视频")
    private MediaVo Broadcast;

}
