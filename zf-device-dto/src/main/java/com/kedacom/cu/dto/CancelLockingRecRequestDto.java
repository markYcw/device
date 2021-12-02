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
@ApiModel(description = "取消录像锁定请求参数类")
public class CancelLockingRecRequestDto extends RecBaseVo implements Serializable {

    @ApiModelProperty("录像开始时间 例如：Tue Nov 30 15:52:34 CST 2021")
    private Date startTime;

    @ApiModelProperty("录像结束时间 例如：Tue Nov 30 15:52:34 CST 2021")
    private Date endTime;

}
