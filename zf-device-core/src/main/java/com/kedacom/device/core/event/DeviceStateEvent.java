package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSONException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import com.kedacom.core.pojo.NotifyHead;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "devstatusnty")
public class DeviceStateEvent implements Notify {

    private NotifyHead nty;

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "父id")
    private String parentId;

    @ApiModelProperty(value = "设备名")
    private String name;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "国标id（20位）")
    private String gbid;

    @ApiModelProperty(value = "ip地址")
    private String ipv4;

    @ApiModelProperty(value = "厂商名")
    private String manufactorCode_name;

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

    @ApiModelProperty(value = "设备更新时间")
    private Date updateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date installDate;

    @ApiModelProperty(value = "父国标id")
    private String parentGbId;

    @ApiModelProperty(value = "域ID")
    private String domainId;

    @ApiModelProperty(value = "操作类型 1:状态更新，2:GPS, 3:新增create，4:修改update, 5:删除delete, 6:自动审核通过，7:表示设备与分组关系发生变化")
    private Integer operateType;


    @Override
    public Integer acquireSsno() {
        return null;
    }

    @Override
    public String acquireCommand() {
        return null;
    }

    @Override
    public <T> T acquireData(Class<T> clazz) throws JSONException {
        return null;
    }
}
