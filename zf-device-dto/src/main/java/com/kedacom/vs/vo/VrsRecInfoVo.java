package com.kedacom.vs.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 录播服务器查询录像信息载体
 * @author ycw
 * @create 2021/06/23 10:25
 */
@Data
public class VrsRecInfoVo {

    @ApiModelProperty("录像总数")
    private Integer total;

    @ApiModelProperty("分页查询HTTP录像信息接口返回列表")
    private List<RecInfoVo> recs;
}
