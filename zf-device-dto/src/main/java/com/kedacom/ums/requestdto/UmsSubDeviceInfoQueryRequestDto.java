package com.kedacom.ums.requestdto;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "分页查询统一平台下子设备信息请求参数类")
public class UmsSubDeviceInfoQueryRequestDto extends BasePage implements Serializable {

    @ApiModelProperty(value = "统一设备Id")
    private String umsId;

    @ApiModelProperty(value = "设备名称，支持模糊查询")
    private String deviceName;

    @ApiModelProperty(value = "支持根据多个设备类型进行检索，OR条件")
    private List<String> deviceTypeList;

    @ApiModelProperty(value = "国标ID")
    private String gbId;

    @ApiModelProperty(value = "设备IP地址")
    private String deviceIp;

    @ApiModelProperty(value = "分组数据库Id，支持模糊搜索")
    private String groupId;

    @ApiModelProperty(value = "设备状态 0在线 1离线 2故障")
    private Integer status;

}
