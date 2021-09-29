package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/29 13:30
 * @description
 */
@Data
@ApiModel(description =  "录像状态")
public class RecStatus {

    @ApiModelProperty(value = "录像文件名 最大字符长度：128个字节")
    private String videoName;

    @ApiModelProperty(value = "录像类型，1-会议录像；2-终端录像；")
    private String recorderType;

    @ApiModelProperty(value = "录像状态 0-未录像；1-正在录像； 2-暂停； 3-正在呼叫实体； 4-准备录像；")
    private Integer state;

    @ApiModelProperty(value = "是否支持免登陆观看直播，0-不支持；1-支持；")
    private Integer anonymous;

    @ApiModelProperty(value = "录像模式 1-录像； 2-直播；3-录像+直播；")
    private Integer recorderMode;

    @ApiModelProperty(value = "是否录主格式码流（视频+音频）0-是；1-否；")
    private Integer mainStream;

    @ApiModelProperty(value = "是否录双流 0-是；1-否；")
    private Integer dualStream;

    @ApiModelProperty(value = "发布类型，0-不发布；1-发布；")
    private Integer publishMode;

    @ApiModelProperty(value = "当前录像进度, 单位为: 秒")
    private Integer currentProgress;

    @ApiModelProperty(value = "开启录像终端数组 仅当开启终端录像有效")
    private List<String> mtInfos;


}
