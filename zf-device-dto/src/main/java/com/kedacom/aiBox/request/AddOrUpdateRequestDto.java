package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/15
 */
@Data
@ApiModel(description = "新增/修改AI-Box设备信息请求参数类")
public class AddOrUpdateRequestDto implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @NotEmpty(message = "设备名称不能为空")
    @ApiModelProperty(value = "名称")
    private String abName;

    @NotBlank(message = "设备IP不能为空")
    @Pattern(regexp = "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$",
            message = "请正确填写设备IP")
    @ApiModelProperty(value = "ip")
    private String abIp;

    @NotNull(message = "设备端口不能为空")
    @ApiModelProperty(value = "端口")
    private Integer abPort;

    @ApiModelProperty(value = "描述")
    private String abDesc;

}
