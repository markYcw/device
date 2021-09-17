package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 15:51
 * @description 编解码设备上报通知内容
 */
@Data
public class Content {

    @ApiModelProperty("当前数量")
    private Integer curNum;

    @ApiModelProperty("是否结束")
    private Integer isEnd;

    @ApiModelProperty("设备列表")
    private List<DeviceInfo> devList;

}
