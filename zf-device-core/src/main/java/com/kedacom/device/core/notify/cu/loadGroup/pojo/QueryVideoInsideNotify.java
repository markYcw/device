package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/2 19:41
 * @description
 */
@Data
public class QueryVideoInsideNotify {

    @ApiModelProperty("是否结束 0:否 1：是")
    private Integer isEnd;

    @ApiModelProperty("录像列表")
    private List<RecInfo> recList;

}
