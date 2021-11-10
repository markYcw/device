package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/10
 */
@Data
@ApiModel(description = "开始浏览码流请求参数类")
public class StopBrowseCodeStreamRequestDto extends CuRequestDto implements Serializable {

    @ApiModelProperty("目的ip")
    @NotBlank(message = "目的IP不能为空")
    private String dstIp;

    @ApiModelProperty("目的端口")
    @NotEmpty(message = "目的端口不能为空")
    private Integer dstPort;

    @ApiModelProperty("puId，开始时必填")
    private String puId;

    @ApiModelProperty("通道号，开始时必填")
    private Integer chn;

    @ApiModelProperty("录像开始时间, 如：202111051200, 开始时必填")
    private String startTime;

    @ApiModelProperty("录像开始时间, 如：202111051200, 开始时必填")
    private String endTime;

    @ApiModelProperty("录像类型，1：平台录像, 2：前端录像, 3：本地录像, 开始时必填")
    private Integer recType;

}
