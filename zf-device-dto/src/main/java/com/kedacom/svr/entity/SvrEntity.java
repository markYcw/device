package com.kedacom.svr.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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


	@ApiModelProperty(value = "ssid")
	private Integer ssid;


	@ApiModelProperty(value = "svr名称")
	private String name;


	@ApiModelProperty(value = "svrIP")
	@NotBlank(message = "IP不能为空")
	private String ip;


	@ApiModelProperty(value = "平台端口")
	private Integer port;

	@ApiModelProperty(value = "svr的websocket端口，一般是9766")
	private Integer webPort;

	@ApiModelProperty(value = "登录svr账号")
	@NotBlank(message = "用户名不能为空")
	private String username;


	@ApiModelProperty(value = "登录平台密码")
	@NotBlank(message = "密码不能为空")
	private String password;


	@ApiModelProperty(value = "SVR版本:1:2.0版本; 2:3.0版本", example = "1：2", required = true)
	@NotNull(message = "devType不能为空")
	private Integer devType;


	@ApiModelProperty(value = "svr型号类型")
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
