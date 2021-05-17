package com.kedacom.ums.requestdto;

import com.kedacom.ums.responsedto.UmsScheduleGroupQueryMediaResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "设置调度组广播源请求参数类")
public class UmsScheduleGroupSetBroadcastRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    private UmsScheduleGroupQueryMediaResponseDto requestDto;

}
