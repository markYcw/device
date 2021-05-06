package com.kedacom.avIntegration.response;

import com.kedacom.avIntegration.data.CellInfo;
import io.swagger.annotations.Api;
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
@ApiModel("大屏布局应答")
public class TvWallLayoutResponse implements Serializable {


    @ApiModelProperty(value = "大屏id")
    private Integer tvid;

    @ApiModelProperty(value = "大屏个数")
    private Integer number;

    @ApiModelProperty(value = "布局窗口信息")
    private List<CellInfo> cellInfos;

}
