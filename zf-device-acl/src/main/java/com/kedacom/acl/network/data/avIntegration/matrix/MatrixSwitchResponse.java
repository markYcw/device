package com.kedacom.acl.network.data.avIntegration.matrix;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "查询矩阵切换应答")
public class MatrixSwitchResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "发生交换的通道个数")
    private Integer number;

    @ApiModelProperty("交换通道信息")
    private List<Exchange> exchange;

    @Data
    class Exchange{

        @ApiModelProperty("信号源ID 音视频信号的国标编号")
        private String srcid;

        @ApiModelProperty("目标通道ID 音视频输出的国标编号")
        private String dstid;

        @ApiModelProperty("交换类型 0=视频、1=音频")
        private Integer type;

        @ApiModelProperty("交换开关 0=关、1=开")
        private Integer opt;
    }



    
}