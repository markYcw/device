package com.kedacom.newMedia.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ycw
 * @describe
 * @date 2022/03/30
 */
@Data
@TableName("ums_new_media")
public class NewMediaEntity implements Serializable {

    @ApiModelProperty(value = "设备id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会话id")
    private Integer ssid;

    @ApiModelProperty(value = "设备名称，必填")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "设备类型, 新媒体平台为7")
    private Integer devType;

    @ApiModelProperty(value = "一机一档ip")
    private String devIp;

    @ApiModelProperty(value = "一机一档端口")
    private Integer devPort;

    @ApiModelProperty(value = "一机一档设备状态订阅ip+端口，多个中间用，隔开")
    private String notifyIp;

    @ApiModelProperty(value = "媒体调度服务IP")
    private String mediaScheduleIp;

    @ApiModelProperty(value = "媒体调度服务端口")
    private Integer mediaSchedulePort;

    @ApiModelProperty(value = "流媒体服务IP")
    private String nmediaIp;

    @ApiModelProperty(value = "流媒体服务端口")
    private Integer nmediaPort;

    @ApiModelProperty(value = "录像服务端口")
    private Integer recPort;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}
