package com.kedacom.device.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 15:55
 * @description 录像信息
 */
@Data
public class RecVo {

    @ApiModelProperty("录像ID")
    private Integer recId;

    @ApiModelProperty("录像记录数据大小，单位MB")
    private Integer size;

    @ApiModelProperty("通道号")
    private String chnId;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("分辨率 0：other 1：d1")
    private String res;

    @ApiModelProperty("哈希校验值")
    private String md5;

}
