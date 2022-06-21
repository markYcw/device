package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hxj
 * @date 2022/6/21 19:34
 */
@Data
@ApiModel(description = "删除会议录像入参")
public class DeleteMeetRecDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @ApiModelProperty(value = "录像开始时间，如果没有录像开始结束时间将删除该录像ID的所有录像")
    private Date startTime;

    @ApiModelProperty(value = "录像结束时间")
    private Date endTime;
}
