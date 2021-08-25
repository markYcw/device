package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/7
 */
@Data
@ApiModel(description =  "删除统一设备信息请求参数类")
public class UmsDeviceInfoDeleteRequestDto implements Serializable {

    @NotEmpty(message = "UMS平台Id不能为空")
    @ApiModelProperty(value = "数据库Id")
    private String umsId;

}
