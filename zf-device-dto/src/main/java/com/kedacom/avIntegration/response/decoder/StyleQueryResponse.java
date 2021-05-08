package com.kedacom.avIntegration.response.decoder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 17:55
 */
@Data
@ApiModel(value = "获取解码通道风格信息应答")
public class StyleQueryResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty("失败时返回信息")
    private String errstr;

    @ApiModelProperty(value = "解码通道风格信息")
    private List<ChnList> chnls;

    @Data
    class ChnList {

        @ApiModelProperty(value = "解码通道ID")
        private String chnid;

        @ApiModelProperty(value = "解码画面风格 - 1=一画面;2=二画面;3=三画面;4=四画面;9=九画面;16=十六画面")
        private Integer style;

        @ApiModelProperty(value = "最大解码能力 - 当前解码通道数")
        private Integer maxchn;

    }

}
