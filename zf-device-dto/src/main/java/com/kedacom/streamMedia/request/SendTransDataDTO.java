package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 15:27
 */
@Data
@ApiModel(description =  "发送透明通道数据")
public class SendTransDataDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备国标id不能为空")
    @ApiModelProperty(value = "设备国标id", required = true)
    private String deviceID;

    @NotNull(message = "透明数据扩展不能为空")
    @ApiModelProperty(value = "是否是透明数据扩展,默认值:0,0:不是、1:是;填默认值就可以", required = true)
    private Integer ext;

    @ApiModelProperty(value = "控制类型。当Ext为1时AppCmd不起作用")
    private Integer appCmd;

    @NotBlank(message = "透明数据不能为空")
    @ApiModelProperty(value = "透明数据;业务方定义-->{svr配置1是走路、TransData发送个1就可以控制他走路}", required = true)
    private String transData;

}
