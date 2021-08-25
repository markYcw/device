package com.kedacom.msp.response.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 09:59
 */
@Data
@ApiModel(description =  "任意开窗应答")
public class TvPlayOpenVO implements Serializable {

    @ApiModelProperty(value = "窗口信息")
    private List<Winds> wnds;

}
