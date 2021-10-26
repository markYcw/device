package com.kedacom.device.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * KM设备消息订阅者信息
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-19 13:33:24
 */
@Data
@TableName("km_listener")
public class KmListenerEntity {

    /**
     * 数据库ID
     */
    @ApiModelProperty("数据库ID")
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    /**
     * 订阅者url
     */
    @ApiModelProperty("订阅者url")
    private String url;

    /**
     * 通知类型
     */
    @ApiModelProperty("通知类型：详情见msgType类")
    private String msgType;


}
