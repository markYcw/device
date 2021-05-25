package com.kedacom.avIntegration.response.tvwall;

import com.kedacom.avIntegration.info.TvWallInfo;
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
@ApiModel("获取大屏列表返回")
public class TvWallListVO implements Serializable {

    @ApiModelProperty(value = "窗口个数")
    private Integer number;

    @ApiModelProperty(value = "大屏信息")
    private List<TvWallInfo> tvinfo;

}
