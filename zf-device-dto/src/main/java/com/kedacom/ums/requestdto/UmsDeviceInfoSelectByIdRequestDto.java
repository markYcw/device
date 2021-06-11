package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/10
 */
@Data
@ApiModel(value = "根据id查询统一设备请求参数类")
public class UmsDeviceInfoSelectByIdRequestDto implements Serializable {

    @NotEmpty(message = "UMS平台Id不能为空")
    @ApiModelProperty(value = "数据库Id")
    private String umsId;

}
