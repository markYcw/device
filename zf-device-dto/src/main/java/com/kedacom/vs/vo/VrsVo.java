package com.kedacom.vs.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * VRS分页查询载体
 * @author ycw
 * @create 2021/05/17 9:25
 */
@Data
public class VrsVo {
    /**
     * VRS标识
     */
    @ApiModelProperty("VRS数据库ID")
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    /**
     * VRS名称
     */
    @ApiModelProperty("VRS名称")
    private String name;

    /**
     * VRS的IP
     */
    @ApiModelProperty("VRS的IP")
    private String ip;
    /**
     * VRS登录账号
     */
    @ApiModelProperty("VRS登录账号")
    private String username;

    /**
     * VRS登录密码
     */
    @ApiModelProperty("VRS登录密码")
    private String password;

    /**
     * 注册VRS返回id
     */
    @ApiModelProperty("注册VRS返回id")
    private Integer ssid;

    /**
     * VRS版本
     */
    @ApiModelProperty("VRS版本")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer version;

    @ApiModelProperty("VRS设备类型")
    private String vrsname;
}
