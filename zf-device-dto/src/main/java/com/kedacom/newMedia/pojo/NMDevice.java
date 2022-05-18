package com.kedacom.newMedia.pojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/4/11 15:37
 * @description 新媒体设备信息
 */
@Data
public class NMDevice {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "设备名")
    private String name;

    @ApiModelProperty(value = "国标id")
    private String gbId;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备ip地址")
    private String ipv4;

    @ApiModelProperty(value = "厂商名")
    private String manufactorCodeName;

    @ApiModelProperty(value = "厂商代码")
    private Integer manufactorCode;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "设备状态 0-在线，1-离线")
    private Integer status;

    @ApiModelProperty(value = "操作时间")
    private Double operateTime;

    @ApiModelProperty(value = "经度")
    private Double latitude;

    @ApiModelProperty(value = "经度字符串")
    private String latitudeStr;

    @ApiModelProperty(value = "维度")
    private Double longitude;

    @ApiModelProperty(value = "维度字符串")
    private String longitudeStr;

    @ApiModelProperty(value = "创建时间（毫秒）")
    private Date createTime;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "行政区域")
    private String civilCodeName;

    @ApiModelProperty(value = "部门")
    private String departmentCodeName;

    @ApiModelProperty(value = "维护人")
    private String mgtMan;

    @ApiModelProperty(value = "联系方式")
    private String mgtUnitContact;

    @ApiModelProperty(value = "安装地址")
    private String address;

    @ApiModelProperty(value = "安装日期（毫秒）")
    private Date installDate;

    @ApiModelProperty(value = "父设备id")
    private String parentId;

    @ApiModelProperty(value = "父设备国标id")
    private String parentGbId;

    @ApiModelProperty(value = "域id")
    private String domainId;

}
