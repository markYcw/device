package com.kedacom.acl.network.data.avIntegration.tvplay;

import com.kedacom.msp.response.tvplay.Winds;
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
public class TvPlayOpenResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "窗口信息")
    private List<Winds> wnds;

}
