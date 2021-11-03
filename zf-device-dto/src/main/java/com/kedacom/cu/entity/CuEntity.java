package com.kedacom.cu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * cu实体类
 * 
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-10-29 15:30
 */
@Data
@TableName("ums_cu")
public class CuEntity {


	@ApiModelProperty(value = "监控平台数据库ID")
	@TableId(value = "id" , type = IdType.AUTO)
	private Integer id;


	@ApiModelProperty(value = "登录成功后返回的ssid")
	private Integer ssid;


	@ApiModelProperty(value = "监控平台名称")
	private String name;


	@ApiModelProperty(value = "监控平台的IP")
	@NotBlank(message = "IP不能为空")
	private String ip;


	@ApiModelProperty(value = "平台端口 2.0平台端口是80")
	private Integer port;


	@ApiModelProperty(value = "登录监控平台账号")
	@NotBlank(message = "用户名不能为空")
	private String username;


	@ApiModelProperty(value = "登录平台密码")
	@NotBlank(message = "密码不能为空")
	private String password;


	@ApiModelProperty(value = "cu版本:0:1.0平台; 1:2.0平台", example = "0：1", required = true)
	private Integer type;


	@ApiModelProperty(value = "cu型号类型")
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
