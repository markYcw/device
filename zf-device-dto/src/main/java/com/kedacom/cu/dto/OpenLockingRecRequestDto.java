package com.kedacom.cu.dto;

import com.kedacom.cu.vo.RecBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
@ApiModel(description = "打开录像锁定请求参数类")
public class OpenLockingRecRequestDto extends RecBaseVo implements Serializable {

    @ApiModelProperty(value = "录像开始时间,例如：2021-09-09T09:30:29.0000 开始时必填", required = true)
    private Date startTime;

    @ApiModelProperty(value = "录像结束时间,例如：2021-09-09T09:30:29.0000 开始时必填", required = true)
    private Date endTime;

}
