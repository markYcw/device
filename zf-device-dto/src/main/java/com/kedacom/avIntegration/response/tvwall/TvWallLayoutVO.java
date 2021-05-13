package com.kedacom.avIntegration.response.tvwall;

import com.kedacom.avIntegration.info.CellInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:27
 */
@Data
@ApiModel("大屏布局返回")
public class TvWallLayoutVO implements Serializable {

    @ApiModelProperty(value = "大屏id")
    private Integer tvid;

    @ApiModelProperty(value = "大屏个数")
    private Integer number;

    @ApiModelProperty(value = "布局窗口信息")
    private List<CellInfo> cellInfos;

}
