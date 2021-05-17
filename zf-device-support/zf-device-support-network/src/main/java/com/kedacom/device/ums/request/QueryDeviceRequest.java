package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@ToString(callSuper = true)
@Data
public class QueryDeviceRequest extends BaseRequest {

    private static final String COMMAND = "querydev";

    @ApiModelProperty(value = "设备id")
    private String f_eq_id;

    @ApiModelProperty(value = "设备名称")
    private String f_like_name;

    @ApiModelProperty(value = "国标id")
    private String f_eq_gbid;

    @ApiModelProperty(value = "设备类型")
    private String f_eq_devicetype;

    @ApiModelProperty(value = "设备ip")
    private String f_eq_ipv4;

    @ApiModelProperty(value = "上级设备id")
    private String f_eq_parentid;

    @ApiModelProperty(value = "设备分组id")
    private String f_eq_groupid;

    @ApiModelProperty(value = "查询起始数")
    private Integer queryindex;

    @ApiModelProperty(value = "查询总数")
    private Integer querycount;

    @Override
    public String name() {
        return COMMAND;
    }

}
