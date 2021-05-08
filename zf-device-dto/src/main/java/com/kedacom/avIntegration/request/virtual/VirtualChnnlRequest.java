package com.kedacom.avIntegration.request.virtual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 18:43
 */
@Data
@ApiModel("录入输入通道列表入参")
public class VirtualChnnlRequest implements Serializable {

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

        @NotEmpty
        @ApiModelProperty("通道类型，魅视的输入通道=46 必填")
        private Integer type;

        @NotEmpty
        @ApiModelProperty("通道所属设备ip地址，网络序 必填")
        private Integer platip;

        @ApiModelProperty
        private String rtsp_ip;

        private String rtsphd_ip;

        private String rtsp4k_ip;

        private Integer stream_type;

    }

}
