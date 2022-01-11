package com.kedacom.vs.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 录播服务器
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-05-14 17:01:24
 */
@Data
@TableName("km_vs")
public class VsEntity {
    /**
     * VRS标识
     */
    @ApiModelProperty("终端标识")
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    /**
     * VRS名称
     */
    @ApiModelProperty("终端名称")
    private String name;

    /**
     * VRS的IP
     */
    @ApiModelProperty("终端IP")
    private String ip;

    /**
     * VRS登录账号
     */
    @ApiModelProperty("终端登录账号")
    private String username;

    /**
     * VRS登录密码
     */
    @ApiModelProperty("终端登录密码")
    private String password;

    /**
     * 注册VRS返回id
     */
    @ApiModelProperty("注册终端返回id")
    private Integer ssid;

    /**
     * VRS版本
     */
    @ApiModelProperty("终端版本")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer version;

    @ApiModelProperty("VRS设备类型")
    private String vrsname;

}
