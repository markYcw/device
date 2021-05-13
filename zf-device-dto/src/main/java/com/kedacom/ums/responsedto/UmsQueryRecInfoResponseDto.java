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
@ApiModel(value = "查询录像信息响应参数类")
public class UmsQueryRecInfoResponseDto implements Serializable {

    @ApiModelProperty(value = "录像开始时间. 2020-10-12 12:00:00")
    private String startTime;

    @ApiModelProperty(value = "录像结束时间. 2020-10-12 13:00:00")
    private String endTime;

    @ApiModelProperty(value = "录像文件大小")
    private Integer recSize;

}
