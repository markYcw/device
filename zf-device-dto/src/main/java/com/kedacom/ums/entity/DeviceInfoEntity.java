package com.kedacom.ums.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Data
@TableName("ums_new_device")
public class DeviceInfoEntity implements Serializable {

    @ApiModelProperty(value = "设备id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "会话id")
    @TableField(value = "session_id")
    private String sessionId;

    @ApiModelProperty(value = "设备名称，必填")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "设备类型, 统一设备为28")
    @TableField(value = "device_type")
    private String deviceType;

    @ApiModelProperty(value = "统一设备IP地址")
    @TableField(value = "device_ip")
    private String deviceIp;

    @ApiModelProperty(value = "统一设备端口")
    @TableField(value = "device_port")
    private Integer devicePort;

    @ApiModelProperty(value = "设备状态订阅IP")
    @TableField(value = "device_notify_ip")
    private String deviceNotifyIp;

    @ApiModelProperty(value = "媒体调度服务IP")
    @TableField(value = "media_ip")
    private String mediaIp;

    @ApiModelProperty(value = "媒体调度服务端口")
    @TableField(value = "media_port")
    private Integer mediaPort;

    @ApiModelProperty(value = "分组Id")
    @TableField(value = "group_id")
    private String groupId;

    @ApiModelProperty(value = "设备状态订阅信息",example = "172.16.1.1:8080,172.16.1.2:9099")
    @TableField(value = "kafka_addr")
    private String kafkaAddr;

    @ApiModelProperty(value = "流媒体服务IP")
    @TableField(value = "streaming_media_ip")
    private String streamingMediaIp;

    @ApiModelProperty(value = "流媒体服务端口")
    @TableField(value = "streaming_media_port")
    private Integer streamingMediaPort;

    @ApiModelProperty(value = "录像服务端口")
    @TableField(value = "streaming_media_rec_port")
    private Integer streamingMediaRecPort;

    @ApiModelProperty(value = "最近一次同步第三方服务子设备时间")
    @TableField(value = "last_sync_third_device_time")
    private String lastSyncThirdDeviceTime;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
