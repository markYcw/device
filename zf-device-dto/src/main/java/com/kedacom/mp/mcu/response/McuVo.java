package com.kedacom.mp.mcu.response;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/13 14:10
 * @description
 */
@Data
public class McuVo {

    /**
     * 会议平台id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 登录成功后会话id
     */
    @ApiModelProperty(value = "登录成功后会话id")
    private String ssid;
    /**
     * 会议平台名称
     */
    @ApiModelProperty(value = "自定义此会议平台名称", required = true)
    @TableField(value = "mcu_name")
    private String name;
    /**
     * 会议平台IP
     */
    @ApiModelProperty(value = "会议平台IP", required = true)
    @NotBlank(message = "会议平台IP不能为空")
    @TableField(value = "mcu_ip")
    private String ip;
    /**
     * 会议平台端口
     */
    @ApiModelProperty(value = "会议平台端口")
    @TableField(value = "mcu_port")
    private Integer port;
    /**
     * 登录会议平台用户名
     */
    @ApiModelProperty(value = "登录会议平台用户名", required = true)
    @NotBlank(message = "登录会议平台用户名不能为空")
    @TableField(value = "mcu_user")
    private String user;
    /**
     * 登录会议平台密码
     */
    @ApiModelProperty(value = "登录会议平台密码", required = true)
    @NotBlank(message = "登录会议平台密码不能为空")
    @TableField(value = "mcu_password")
    private String password;
    /**
     * 获取token使用
     */
    @ApiModelProperty(value = "获取token使用", required = true)
    @NotBlank(message = "key不能为空")
    @TableField(value = "mcu_key")
    private String sdkkey;
    /**
     * 获取token使用
     */
    @ApiModelProperty(value = "获取token使用", required = true)
    @NotBlank(message = "secret不能为空")
    @TableField(value = "mcu_secret")
    private String sdksecret;
    /**
     * 会议平台版本
     */
    @ApiModelProperty(value = "会议平台版本:1、mcu4.7;4、mcu5.0", example = "1、mcu4.7;4、mcu5.0", required = true)
    @TableField(value = "mcu_type")
    private Integer devtype;
    /**
     * 会议平台版本名称
     */
    @ApiModelProperty(value = "会议平台版本名称:5.0平台、4.7平台等", example = "例如: 5.0平台", required = true)
    @TableField(value = "mcu_version")
    private String mcuname;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifyTime;

}
