package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 11:06
 * @description
 */
@ApiModel(description = "获取音频能力集响应")
@Data
public class GetAudioCapVO implements Serializable {

    @ApiModelProperty("音频采集数量")
    private Integer audCapNum;

    @ApiModelProperty("音频MicIn数量")
    private Integer audMicInNum;

    @ApiModelProperty("音频LineIn数量")
    private Integer audLineInNum;

    @ApiModelProperty("音频JackPhoneIn数量")
    private Integer audJackPhoneInNum;

    @ApiModelProperty("音频DMicIn数量")
    private Integer audDMicInNum;

    @ApiModelProperty("音频DviIn数量")
    private Integer audDviInNum;

    @ApiModelProperty("音频atbox网络通道数量")
    private Integer audAtboxNetChnNum;

    @ApiModelProperty("音频网络通道数量")
    private Integer audNetChnNum;

    @ApiModelProperty("音频本地网络通道数量")
    private Integer audLocalNetChnNum;

    @ApiModelProperty("音频远程点网络通道数量")
    private Integer audRemNetChnNum;

    @ApiModelProperty("音频输入列表 0:SVR_AUD_INTF_MACIN 1:SVR_AUD_INTF_LINEIN 2:SVR_AUD_INTF_JACKIN 3:SVR_AUD_INTF_DMICIN 4:SVR_AUD_INTF_DVIIN 5:SVR_AUD_INTF_ATBOXIN " +
            "6:SVR_AUD_INTF_LOCAL_NETIN 7:SVR_AUD_INTF_REM_NETIN")
    private List<Integer> audIn;


}
