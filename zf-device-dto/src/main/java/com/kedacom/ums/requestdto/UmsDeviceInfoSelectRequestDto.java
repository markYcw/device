package com.kedacom.ums.requestdto;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/7
 */
@Data
@ApiModel(description =  "查询统一设备请求参数类")
public class UmsDeviceInfoSelectRequestDto extends BasePage implements Serializable {

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备类型, 统一设备为28")
    private String deviceType;

}
