package com.kedacom.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("统一设备变化通知类")
public class UmsSubDeviceChangeModel implements Serializable {

    @ApiModelProperty("通知类型：0，新增；1，修改；2，删除")
    private Integer ntyType;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "上级")
    private String parentId;

    @ApiModelProperty(value = "国标id（20位）")
    private String gbid;

    @ApiModelProperty(value = "ip地址")
    private String deviceIp;

    @ApiModelProperty(value = "分组Id")
    private String groupId;

    @ApiModelProperty(value = "设备状态（0：在线 1：离线 2：故障）")
    private Integer deviceStatus;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "经度 字符串")
    private String longitudeStr;

    @ApiModelProperty(value = "纬度")
    private Double latitude;

    @ApiModelProperty(value = "纬度 字符串")
    private String latitudeStr;

    @ApiModelProperty(value = "型号")
    private String deviceModel;

    @ApiModelProperty(value = "行政区域")
    private String civilName;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "域ID")
    private String domainId;

    @ApiModelProperty("时间戳")
    private long timeStamp;

}
