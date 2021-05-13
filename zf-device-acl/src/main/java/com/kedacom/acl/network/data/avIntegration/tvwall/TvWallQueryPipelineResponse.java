package com.kedacom.acl.network.data.avIntegration.tvwall;

import com.kedacom.avIntegration.info.TvWallWndInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:37
 */
@Data
@ApiModel("查询大屏通道绑定应答")
public class TvWallQueryPipelineResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "大屏ID")
    private Integer id;

    @ApiModelProperty(value = "解码通道使用模式 - 0=外置、1=内置，不填或填0时为外置")
    private Integer dec_mode;

    @ApiModelProperty(value = "窗口信息")
    private List<TvWallWndInfo> wndinfo;

}
