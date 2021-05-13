package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "分页查询统一平台下子设备信息响应参数类")
public class UmsSubDeviceInfoQueryResponseDto implements Serializable {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "统一平台ID")
    private String umsId;

    @ApiModelProperty(value = "设备名")
    private String name;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "国标id（20位）")
    private String gbid;

    @ApiModelProperty(value = "ip地址")
    private String deviceIp;

    @ApiModelProperty(value = "厂商名")
    private String manufactorName;

    @ApiModelProperty(value = "厂商代码")
    private Integer manufactorCode;

    @ApiModelProperty(value = "分组Id")
    private String groupId;

    @ApiModelProperty(value = "设备状态（0：在线 1：离线 2：故障）")
    private Integer status;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "经度 字符串")
    private String longitudeStr;

    @ApiModelProperty(value = "纬度")
    private Double latitude;

    @ApiModelProperty(value = "纬度 字符串")
    private String latitudeStr;

    @ApiModelProperty(value = "设备创建时间")
    private Date createTime;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "行政区域")
    private String civilName;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "维护人")
    private String maintainMan;

    @ApiModelProperty(value = "联系方式")
    private String maintainContact;

    @ApiModelProperty(value = "安装地址")
    private String address;

    @ApiModelProperty(value = "安装时间")
    private Date installDate;

    @ApiModelProperty(value = "设备模式 0正常 1同步中 2已丢失")
    private Integer deviceMod;

    @ApiModelProperty(value = "域ID")
    private String domainId;

}
