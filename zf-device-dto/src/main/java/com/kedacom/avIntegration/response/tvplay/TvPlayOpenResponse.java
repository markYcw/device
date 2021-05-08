package com.kedacom.avIntegration.response.tvplay;

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
@ApiModel(value = "任意开窗应答")
public class TvPlayOpenResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "窗口信息")
    private List<Winds> wnds;

    @Data
    class Winds {

        @ApiModelProperty(value = "窗口ID")
        private Integer wndid;

    }

}
