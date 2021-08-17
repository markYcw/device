package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 11:09
 * @description 合成参数
 */
@Data
@ApiModel(value = "合成参数")
public class MixInfo implements Serializable {

    @ApiModelProperty(value = "是否广播:0：不广播;1：广播")
    private Integer isBroadCast;

    @ApiModelProperty(value = "是否自动合成:0：不自动合成;1：自动合成")
    private Integer isAuto;

    @ApiModelProperty(value = "风格")
    private Integer style;

    @ApiModelProperty(value = "终端列表")
    private List<MtInfo> mtInfos;

}
