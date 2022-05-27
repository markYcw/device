package com.kedacom.newMedia.dto;

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
@ApiModel(description =  "远程点信息，type为1时必填")
public class RemotePoint {

    @NotBlank(message = "远程点名称不能为空")
    @ApiModelProperty(value = "远程点名称", required = true)
    private String name;

    @NotBlank(message = "远程点url不能为空")
    @ApiModelProperty(value = "远程点url", required = true)
    private String url;


}
