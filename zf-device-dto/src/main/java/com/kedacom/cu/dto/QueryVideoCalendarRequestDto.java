package com.kedacom.cu.dto;

import com.kedacom.cu.vo.RecBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/15
 */
@Data
@ApiModel(description = "查询录像日历请求参数类")
public class QueryVideoCalendarRequestDto extends RecBaseVo implements Serializable {

    @NotBlank(message = "录像开始时间不能为空")
    @ApiModelProperty("录像开始时间, eg : 202111051200")
    private String startTime;

    @NotBlank(message = "录像结束时间不能为空")
    @ApiModelProperty("录像结束时间, eg : 202111051200")
    private String endTime;

    @NotEmpty(message = "录像类别不能为空")
    @ApiModelProperty("录像类别： 1：平台录像; 2：前端录像; 3：本地录像\n")
    private Integer recType;

}
