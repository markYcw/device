package com.kedacom.mp.mcu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 会议平台
 *
 * @author hxj
 * @email hexijian@kedacom.com
 * @date 2021-08-12 10:19:30
 */
@Data
@TableName("ums_mcu")
public class UmsMcuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会议平台id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String name;
    /**
     * 会议平台IP
     */
    @ApiModelProperty(value = "会议平台IP", required = true)
    @NotBlank(message = "会议平台IP不能为空")
    private String ip;
    /**
     * 会议平台端口
     */
    @ApiModelProperty(value = "会议平台端口")
    private Integer port;
    /**
     * 登录会议平台用户名
     */
    @ApiModelProperty(value = "登录会议平台用户名", required = true)
    @NotBlank(message = "登录会议平台用户名不能为空")
    private String user;
    /**
     * 登录会议平台密码
     */
    @ApiModelProperty(value = "登录会议平台密码", required = true)
    @NotBlank(message = "登录会议平台密码不能为空")
    private String password;
    /**
     * 获取token使用
     */
    @ApiModelProperty(value = "获取token使用", required = true)
    @NotBlank(message = "key不能为空")
    private String mcuKey;
    /**
     * 获取token使用
     */
    @ApiModelProperty(value = "获取token使用", required = true)
    @NotBlank(message = "secret不能为空")
    private String secret;
    /**
     * 会议平台版本
     */
    @ApiModelProperty(value = "会议平台版本:1、mcu4.7;2、mcu5.0", required = true)
    private Integer mcuType;
    /**
     * 会议平台版本名称
     */
    @ApiModelProperty(value = "会议平台版本名称,例如mcu5.0", required = true)
    private String mcuVersion;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}
