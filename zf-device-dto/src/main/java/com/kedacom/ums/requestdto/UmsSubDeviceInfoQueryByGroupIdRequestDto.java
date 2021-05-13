package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@ApiModel(value = "根据groupId查询子设备请求参数类")
public class UmsSubDeviceInfoQueryByGroupIdRequestDto implements Serializable {

    @NotNull(message = "统一平台Id")
    @ApiModelProperty(value = "统一平台Id")
    private String umsId;

    @ApiModelProperty(value = "分组数据库Id，支持模糊搜索")
    private String groupId;

}
