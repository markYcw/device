package com.kedacom.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Data
@TableName("ums_new_sub_device")
public class SubDeviceInfoEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "设备id")
    private String id;

    @TableField(value = "ums_id")
    @ApiModelProperty(value = "统一平台ID")
    private String umsId;

    @TableField(value = "name")
    @ApiModelProperty(value = "设备名")
    private String name;

    @TableField(value = "device_type")
    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @TableField(value = "gbid")
    @ApiModelProperty(value = "国标id（20位）")
    private String gbid;

    @TableField(value = "device_ip")
    @ApiModelProperty(value = "ip地址")
    private String deviceIp;

    @TableField(value = "manufactor_name")
    @ApiModelProperty(value = "厂商名")
    private String manufactorName;

    @TableField(value = "manufactor_code")
    @ApiModelProperty(value = "厂商代码")
    private Integer manufactorCode;

    @TableField(value = "group_id")
    @ApiModelProperty(value = "分组Id")
    private String groupId;

    @TableField(value = "status")
    @ApiModelProperty(value = "设备状态（0：在线 1：离线 2：故障）")
    private Integer status;

    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度")
    private Double longitude;

    @TableField(value = "longitude_tr")
    @ApiModelProperty(value = "经度 字符串")
    private String longitudeStr;

    @TableField(value = "latitude")
    @ApiModelProperty(value = "纬度")
    private Double latitude;

    @TableField(value = "latitude_str")
    @ApiModelProperty(value = "纬度 字符串")
    private String latitudeStr;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "设备创建时间")
    private Date createTime;

    @TableField(value = "model")
    @ApiModelProperty(value = "型号")
    private String model;

    @TableField(value = "civil_name")
    @ApiModelProperty(value = "行政区域")
    private String civilName;

    @TableField(value = "department_name")
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @TableField(value = "maintain_man")
    @ApiModelProperty(value = "维护人")
    private String maintainMan;

    @TableField(value = "maintain_contact")
    @ApiModelProperty(value = "联系方式")
    private String maintainContact;

    @TableField(value = "address")
    @ApiModelProperty(value = "安装地址")
    private String address;

    @TableField(value = "install_date")
    @ApiModelProperty(value = "安装时间")
    private Date installDate;

    @TableField(value = "device_mod")
    @ApiModelProperty(value = "设备模式 0正常 1同步中 2已丢失")
    private Integer deviceMod;

    @TableField(value = "domain_id")
    @ApiModelProperty(value = "域ID")
    private String domainId;

    @TableField(value = "pinyin")
    @ApiModelProperty(value = "设备名称(拼音+首字母)")
    private String pinyin;

}
