package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author ycw
 */
@ApiModel(description = "Cu/监控平台配置信息")
@Data
public class DevEntityVo {

    /**
     * 设备数据库Id
     */
    @ApiModelProperty(value = "设备数据库Id")
    private Integer id;

    /**
     * 监控平台名称
     */
    @ApiModelProperty(value = "设备名称",required = true)
    private String name;

    /**
     * 监控平台IP
     */
    @NotBlank(message = "IP不能为空")
    @ApiModelProperty(value = "监控平台的IP",required = true)
    private String ip;

    @ApiModelProperty(value = "NAT穿越IP")
    private String natIp;

    /**
     * 监控平台端口
     */
    @Min(value = 0, message = "设备端口号参数不正确")
    @Max(value = 65536, message = "设备端口号参数不正确")
    @ApiModelProperty(value = "设备端口号 2.0平台端口是80",required = true)
    private Integer port;

    @ApiModelProperty(value = "NAT穿越PORT")
    private Integer natPort;

    /**
     * 监控登录平台账号
     */
    @ApiModelProperty(value = "登录监控平台账号",required = true)
    @NotBlank(message = "登录监控平台账号不能为空")
    private String username;

    /**
     * 监控登录平台密码
     */
    @ApiModelProperty(value = "监控登录平台密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String password;


    @ApiModelProperty("分组信息")
    private List<CuGroupVo> childList;

    @ApiModelProperty("在线状态 0：离线 1：在线")
    private Integer status;

    @ApiModelProperty("平台域id")
    private String domainId;

}
