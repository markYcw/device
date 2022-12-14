package com.kedacom.cu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
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
public class CuEntity implements Serializable {


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

	@TableField(updateStrategy = FieldStrategy.IGNORED) //更新时Set值为null生效
	@ApiModelProperty(value = "NAT穿越PORT")
	private Integer natPort;

	@ApiModelProperty(value = "登录监控平台账号")
	@NotBlank(message = "用户名不能为空")
	private String username;

	@ApiModelProperty(value = "用户编号")
	private String userNo;


	@ApiModelProperty(value = "登录平台密码")
	@NotBlank(message = "密码不能为空")
	private String password;


	@ApiModelProperty(value = "cu版本:8:1.0平台; 9:2.0平台", example = "8 ：9", required = true)
	private Integer type;


	@ApiModelProperty(value = "平台域id")
	private String modelType;

	@TableField(updateStrategy = FieldStrategy.IGNORED) //更新时Set值为null生效
	@ApiModelProperty(value = "NAT穿越IP")
	private String natIp;

	//实体与表字段忽略映射
	@TableField(exist = false)
	@ApiModelProperty("在线状态 0：离线 1：在线")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifyTime;





}
