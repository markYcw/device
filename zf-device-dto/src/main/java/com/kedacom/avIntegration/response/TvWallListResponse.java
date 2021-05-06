package com.kedacom.avIntegration.response;

import com.kedacom.avIntegration.data.TvInfo;
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

    @ApiModelProperty(value = "窗口个数")
    private Integer number;

    @ApiModelProperty(value = "大屏信息")
    private List<TvInfo> list;

}
