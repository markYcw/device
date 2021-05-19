package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
public class SubDeviceInfoResponseVo implements Serializable {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "设备名")
    private String name;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "国标id")
    private String gbid;

    @ApiModelProperty(value = "设备ip")
    private String ipv4;

    @ApiModelProperty(value = "厂商名")
    private String manufactorCode_name;

    @ApiModelProperty(value = "厂商编码")
    private Integer manufactorCode;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "设备状态0 在线 ，1 离线， 2 故障")
    private String status;

    @ApiModelProperty(value = "纬度")
    private Double latitude;

    @ApiModelProperty(value = "纬度-字符串")
    private String latitudeStr;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "经度-字符串")
    private String longitudeStr;

    @ApiModelProperty(value = "设备创建时间")
    private Date createTime;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "行政区域")
    private String civilCode_name;

    @ApiModelProperty(value = "部门名称")
    private String departmentCode_name;

    @ApiModelProperty(value = "维护人")
    private String mgtMan;

    @ApiModelProperty(value = "联系方式")
    private String mgtUnitContact;

    @ApiModelProperty(value = "安装地址")
    private String address;

    @ApiModelProperty(value = "安装时间")
    private Date installDate;

    @ApiModelProperty(value = "父节点设备id")
    private String parentId;

    @ApiModelProperty(value = "父节点国标id")
    private String parentGbId;

    @ApiModelProperty(value = "域ID")
    private String domainId;

}
