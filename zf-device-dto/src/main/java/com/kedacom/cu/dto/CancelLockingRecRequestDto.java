package com.kedacom.cu.dto;

import com.kedacom.cu.vo.RecBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
@ApiModel(description = "取消录像锁定请求参数类")
public class CancelLockingRecRequestDto extends RecBaseVo implements Serializable {

    @NotBlank(message = "录像开始时间不能为空")
    @ApiModelProperty(value = "录像开始时间, eg : 202111051200", required = true)
    private String startTime;

    @NotBlank(message = "录像结束时间不能为空")
    @ApiModelProperty(value = "录像结束时间, eg : 202111051200", required = true)
    private String endTime;

}
