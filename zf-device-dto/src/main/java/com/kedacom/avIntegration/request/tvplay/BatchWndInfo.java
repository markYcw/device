package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:37
 */
@Data
@ApiModel(" 窗口显示-批量窗口信息")
public class BatchWndInfo implements Serializable {

    @NotNull(message = "窗口位置ID不能为空")
    @ApiModelProperty(value = "必填 - 窗口位置ID")
    private Integer wnd_id;

    @NotNull(message = "窗口内小窗口索引不能为空")
    @ApiModelProperty(value = "必填 - 窗口内小窗口索引， 从0开始，从上到下，从左到右排布")
    private Integer wnd_index;

    @NotBlank(message = "信号源ID（UUID）不能为空")
    @ApiModelProperty(value = "必填 - 信号源ID（UUID）")
    private String chnid;

    @NotNull(message = "信号源类型不能为空")
    @ApiModelProperty(value = "必填 - 信号源类型，0=监控源、1=画面合成源或者会议终端")
    private Integer chntype;

    @ApiModelProperty(value = "必填 - 会议终端的国标ID，只有是会议终端时有效")
    private String chnidex;

    @ApiModelProperty("参与混音的设备列表")
    private List<String> mixer;

    @ApiModelProperty(value = "包传输方式 - 0=单播，1=组播，默认单播")
    private Integer protocol;

    @ApiModelProperty(value = "组播地址信息、注意：组播只针对网络流")
    private MulticastAddress multicast;

}
