package com.kedacom.newMedia.dto;

import com.kedacom.streamMedia.request.RemDev;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : ycw
 * @date : 2022/05/26 10:12
 */
@Data
@ApiModel(description =  "发送宏指令数据")
public class SVROrderDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private Integer umsId;

    @NotBlank(message = "设备国标id不能为空")
    @ApiModelProperty(value = "设备国标id", required = true)
    private String deviceId;

    @ApiModelProperty(value = "宏指令 type为0时必填")
    private String orderData;

    @NotNull(message = "宏指令类型不能为空")
    @ApiModelProperty(value = "宏指令类型,0:常规;1：添加启用远程点(此时需要传入RemDev参数)")
    private Integer type;

    @ApiModelProperty("远程点信息，type为1时必填")
    private RemotePoint remotePoint;


}
