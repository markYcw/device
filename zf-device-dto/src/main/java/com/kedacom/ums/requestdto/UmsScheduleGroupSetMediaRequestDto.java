package com.kedacom.ums.requestdto;

import com.kedacom.ums.responsedto.UmsScheduleGroupQueryMediaResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "设置调度组成员媒体源请求参数类")
public class UmsScheduleGroupSetMediaRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    private UmsScheduleGroupQueryMediaResponseDto mediaInfo;

}
