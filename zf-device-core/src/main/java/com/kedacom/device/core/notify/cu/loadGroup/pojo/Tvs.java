package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电视墙。
 * @author ycw
 */
@Data
public class Tvs {

	@ApiModelProperty("电视墙id")
	private String tvId;

	@ApiModelProperty("电视墙id")
	private Divs divs;
	
}
