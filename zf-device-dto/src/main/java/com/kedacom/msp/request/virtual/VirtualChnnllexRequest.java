package com.kedacom.msp.request.virtual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 18:43
 */
@Data
@ApiModel("录入输入通道列表扩展入参")
public class VirtualChnnllexRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @ApiModelProperty("选填 设备id，可通过pcsmc工具查询到")
    private Integer devid;

    private List<Chnnl> chns;

    @Data
    class Chnnl{

        @ApiModelProperty("大屏在设备上的私有id 选填")
        private Integer id;

        @NotEmpty
        @ApiModelProperty("大屏在设备上的名称 必填")
        private String name;

        @NotNull
        @ApiModelProperty("通道类型，魅视的输入通道=46 必填")
        private Integer type;

        @ApiModelProperty("魅视输入通道rtsp地址 选填")
        private String rtsp_ip;

        @ApiModelProperty("魅视输入通道rtsp高清地址 选填")
        private String rtsphd_ip;

        @ApiModelProperty("魅视输入通道rtsp 4k高清地址 选填")
        private String rtsp4k_ip;

        @ApiModelProperty("魅视信号源rtsp地址类型，4=rtsp高清，7=rtsp，10=4k高清 选填")
        private Integer stream_type;

    }

}
