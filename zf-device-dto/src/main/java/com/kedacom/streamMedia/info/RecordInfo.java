package com.kedacom.streamMedia.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 09:20
 */
@Data
@ApiModel(description = "录像信息")
public class RecordInfo implements Serializable {

    @ApiModelProperty("本段录像的开始时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2020-10-01T00:00:00")
    private String start_time;

    @ApiModelProperty("本段录像的结束时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2020-10-01T00:00:00")
    private String end_time;

    @ApiModelProperty("文件大小")
    private String file_size;

}
