package com.kedacom.streamMedia.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 18:53
 */
@Data
@ApiModel("音频设备叠加图片参数")
public class BackgroundPicParam implements Serializable {

    @ApiModelProperty("纯音频设备的设备类型（根据此类型来叠加右下角和中间位置图片在底片上），目前有以下 6种：1-对讲机，2-固话，3-会议终端，4-视信通，5-手机，6-监控摄像机注：能获取到图片时，中间位置不叠加图片和文字，在左上角叠加设备名称的文字；获取不到图片或者不指定图片源时，使用默认的蓝底图片，中间位置叠加设备类型图片和文字。")
    private Integer device_type;

}
