package com.kedacom.streamMedia.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:13
 */
@Data
@ApiModel("")
public class VideoMixer implements Serializable {

    @ApiModelProperty("画面合成通道索引，从0开始")
    private Integer winIndex;

    @ApiModelProperty("参与混音方终端ID")
    private String resourceID;

    @ApiModelProperty("域ID。")
    private Integer nmediaId;

    @ApiModelProperty("显示背景图。默认为 0-不显示；1、会议终端；2、执法记录仪；3、单兵；4、IPC；5、人像卡口；6、视信通；7、手机；8、固话；9、350M；10、通用类型；99、自定义背景图片地址（通过 pic_url 传入）。1080p、layout=1 时，图片大小为960*540，居中显示，会根据 layout等比例缩放")
    private Integer backgroundPic;

    @ApiModelProperty("自定义背景图片地址，当background_pic=99 时必填")
    private String picUrl;

    @ApiModelProperty("资源别名，最大 32 个字符。show_text =1 时生效。")
    private String recAlias;

}
