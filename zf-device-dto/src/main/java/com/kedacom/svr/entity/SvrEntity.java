package com.kedacom.svr.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * svr实体类
 * 
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-09-06 10:30
 */
@Data
@TableName("ums_svr")
public class SvrEntity {


	@ApiModelProperty(value = "svr标识")
	@TableId(value = "id" , type = IdType.AUTO)
	private Integer id;

	@TableField(updateStrategy = FieldStrategy.IGNORED) //更新时Set值为null生效
	@ApiModelProperty(value = "ssid登录SVR后返回的sessionID保存SVR时可不传")
	private Integer ssid;

    @NotBlank(message = "svr名称不能为空")
	@ApiModelProperty(value = "svr名称",required = true)
	private String name;


	@ApiModelProperty(value = "svr的IP",required = true)
	@NotBlank(message = "IP不能为空")
	private String ip;

	@Min(value = 0, message = "设备端口号参数不正确")
	@Max(value = 65536, message = "设备端口号参数不正确")
	@ApiModelProperty(value = "平台端口 SVR2931端口为80 SVR2930端口为9765 其他型号均为8765",required = true)
	private Integer port;

	@ApiModelProperty(value = "svr的websocket端口，SVR2931端口为8780 SVR2930端口为9766 其他型号均为8766",required = true)
	private Integer webPort;

	@ApiModelProperty(value = "登录svr账号",required = true)
	@NotBlank(message = "用户名不能为空")
	private String username;


	@ApiModelProperty(value = "登录平台密码",required = true)
	@NotBlank(message = "密码不能为空")
	private String password;


	@ApiModelProperty(value = "SVR版本:5:2.0版本; 6:3.0版本", example = "1：2")
	private Integer devType;

	@NotBlank(message = "svr型号类型不能为空")
	@ApiModelProperty(value = "svr型号类型",required = true)
	private String modelType;

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
