package com.kedacom.cu.dto;

import com.kedacom.cu.vo.RecBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/15
 */
@Data
@ApiModel(description = "查询录像请求参数类")
public class QueryVideoRequestDto extends RecBaseVo implements Serializable {

    @ApiModelProperty(value = "录像开始时间 例如：2021-09-09T09:30:29.0000", required = true)
    private Date startTime;

    @ApiModelProperty(value = "录像结束时间 例如：2021-09-09T09:30:29.0000", required = true)
    private Date endTime;

    @NotNull(message = "录像类别不能为空")
    @ApiModelProperty(value = "录像类别： 1：平台录像; 2：前端录像; 3：本地录像", required = true)
    private Integer recType;

}
