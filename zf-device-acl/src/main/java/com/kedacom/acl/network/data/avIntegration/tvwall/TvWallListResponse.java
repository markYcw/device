package com.kedacom.acl.network.data.avIntegration.tvwall;

import com.kedacom.msp.info.TvWallInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 11:06
 */
@Data
@ApiModel("获取大屏列表应答")
public class TvWallListResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "窗口个数")
    private Integer number;

    @ApiModelProperty(value = "大屏信息")
    private List<TvWallInfo> tvinfo;

}
