package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 12:05
 * @description
 */
@Data
@ApiModel(description = "获取音频能力集响应")
@ToString(callSuper = true)
public class GetAudioCapResponse extends BaseResponse {

    @ApiModelProperty("音频采集数量")
    private Integer AudCapNum;

    @ApiModelProperty("音频MicIn数量")
    private Integer AudMicInNum;

    @ApiModelProperty("音频LineIn数量")
    private Integer AudLineInNum;

    @ApiModelProperty("音频JackPhoneIn数量")
    private Integer AudJackPhoneInNum;

    @ApiModelProperty("音频DMicIn数量")
    private Integer AudDMicInNum;

    @ApiModelProperty("音频DviIn数量")
    private Integer AudDviInNum;

    @ApiModelProperty("音频atbox网络通道数量")
    private Integer AudAtboxNetChnNum;

    @ApiModelProperty("音频网络通道数量")
    private Integer AudNetChnNum;

    @ApiModelProperty("音频本地网络通道数量")
    private Integer AudLocalNetChnNum;

    @ApiModelProperty("音频远程点网络通道数量")
    private Integer AudRemNetChnNum;


    @ApiModelProperty("音频输入列表 0:SVR_AUD_INTF_MACIN 1:SVR_AUD_INTF_LINEIN 2:SVR_AUD_INTF_JACKIN 3:SVR_AUD_INTF_DMICIN 4:SVR_AUD_INTF_DVIIN 5:SVR_AUD_INTF_ATBOXIN " +
            "6:SVR_AUD_INTF_LOCAL_NETIN 7:SVR_AUD_INTF_REM_NETIN")
    private List<Integer> AudIn;

}
