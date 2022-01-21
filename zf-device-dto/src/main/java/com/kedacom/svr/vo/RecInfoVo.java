package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SVR录像信息
 * @author ycw
 * @create 2021/4/19 15:44
 */
@Data
public class RecInfoVo {

    @ApiModelProperty("本次查询总数")
    private int total;

    @ApiModelProperty("录像记录ID，唯一")
    private int id;


    @ApiModelProperty("录像记录数据大小，单位MB")
    private int size;


    @ApiModelProperty("录像码流分辨率 1:D1, 0:other")
    private int resolution;


    @ApiModelProperty("通道id信息")
    private String chn;


    @ApiModelProperty("开始时间")
    private String starttime;


    @ApiModelProperty("结束时间")
    private String endtime;


    @ApiModelProperty("MD5校验码")
    private String md5;
}
