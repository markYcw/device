package com.kedacom.cu.vo;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/2 19:28
 * @description 查询录像通知
 */
@Data
public class QueryCuVideoVo extends DeviceNotifyRequestDTO {

    @ApiModelProperty("是否结束 0:否 1：是")
    private Integer isEnd;

    @ApiModelProperty("录像列表")
    private List<RecInfoVo> recList;
}
