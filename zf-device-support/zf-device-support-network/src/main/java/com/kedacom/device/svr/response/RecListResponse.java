package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;

import com.kedacom.device.svr.pojo.RecVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 15:59
 * @description
 */
public class RecListResponse extends SvrResponse {

    @ApiModelProperty("录像列表")
    private List<RecVo> recList;
}