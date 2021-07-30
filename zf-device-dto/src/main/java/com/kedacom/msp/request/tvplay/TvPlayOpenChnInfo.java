package com.kedacom.msp.request.tvplay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class TvPlayOpenChnInfo implements Serializable {

    @NotBlank(message = "信号源ID不能为空")
    @ApiModelProperty(value = "信号源ID - 必填，国标ID或资源ID", required = true)
    private String chnid;

    @NotNull(message = "信号源类型不能为空")
    @ApiModelProperty(value = "信号源类型 - 必填，0=监控源、1=画面合成源或者会议终端、2=调度资源（需通过第三方服务操作）", required = true)
    private Integer chntype;

    @ApiModelProperty(value = "信号源名称 - 信号源为资源ID时，需设置资源对应的名称，显示使用")
    private String chnname;

    @NotNull(message = "小窗口索引不能为空")
    @ApiModelProperty(value = "小窗口索引 - 必填，四画面时，信号源显示在小窗口的位置", required = true)
    private Integer index;

    @ApiModelProperty(value = "混音列表:参与混音的设备列表")
    private List<String> mixer;

    @ApiModelProperty(value = "包传输方式 - 0=单播，1=组播，默认单播")
    private Integer protocol;

    @ApiModelProperty(value = "组播地址信息")
    private TvPlayOpenMulticast tvPlayOpenMulticast;

    @ApiModelProperty(value = "会议终端的国标ID - 注意老版本融合调度系统，只有是会议终端时有效，新版本融合调度系统该字段不用")
    private String chnidex;

}
