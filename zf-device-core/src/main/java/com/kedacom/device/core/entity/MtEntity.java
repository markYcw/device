package com.kedacom.device.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/30
 */
@Data
@TableName("ums_mt")
public class MtEntity {

    @ApiModelProperty("终端标识")
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("终端名称")
    private String name;

    @ApiModelProperty("终端IP")
    private String ip;

    @ApiModelProperty("终端端口")
    private Integer port;

    @ApiModelProperty("终端登录账号")
    private String username;

    @ApiModelProperty("终端登录密码")
    private String password;

    @ApiModelProperty("注册终端返回id")
    private Integer mtid;

    @ApiModelProperty("终端设备版本，0:3.0; 1:5.0")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer devtype;

    @ApiModelProperty("终端设备类型名称")
    private String mtname;

    @ApiModelProperty("会议终端e164号")
    private String e164;

    @ApiModelProperty("UPU名称")
    private String upuname;

}
