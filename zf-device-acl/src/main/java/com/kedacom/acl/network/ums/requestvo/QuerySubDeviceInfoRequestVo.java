package com.kedacom.acl.network.ums.requestvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
@ApiModel
public class QuerySubDeviceInfoRequestVo implements Serializable {

    @ApiModelProperty(value = "设备id")
    private String f_eq_id;

    @ApiModelProperty(value = "设备名称")
    private String f_like_name;

    @ApiModelProperty(value = "设备id")
    private String f_eq_gbid;

    @ApiModelProperty(value = "设备id")
    private String f_eq_devicetype;

    @ApiModelProperty(value = "设备id")
    private String f_eq_ipv4;

    @ApiModelProperty(value = "设备id")
    private String f_eq_parentid;

    @ApiModelProperty(value = "设备id")
    private String f_eq_groupid;

    @ApiModelProperty(value = "设备id")
    private Integer queryindex;

    @ApiModelProperty(value = "设备id")
    private Integer querycount;

}
