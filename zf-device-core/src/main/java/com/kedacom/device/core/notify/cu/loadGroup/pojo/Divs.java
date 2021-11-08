package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电视机。
 * @author ycw
 */
@Data
public class Divs {

	@ApiModelProperty("电视机id")
	private String id;

	@ApiModelProperty("播放模式 1：实时视频 2：平台录像 3：前端录像")
	private String mode;

	@ApiModelProperty("录像信息")
	private Recs rec;

	@ApiModelProperty("轮询状态，0：无效，1：开始，2：停止，3：暂停")
	private Integer sate;

	@ApiModelProperty("编码器puid")
	private String puId;

	@ApiModelProperty("编码器通道")
	private Integer chn;

	@ApiModelProperty("当前浏览请求出现的错误")
	private Integer lookErr;

}
