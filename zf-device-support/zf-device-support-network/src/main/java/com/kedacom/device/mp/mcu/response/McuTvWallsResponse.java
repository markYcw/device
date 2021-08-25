package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import com.kedacom.mp.mcu.pojo.TvWall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:43
 * @description 获取电视墙列表中间件响应
 */
@Data
@ApiModel(description =  "获取电视墙列表中间件响应")
public class McuTvWallsResponse extends MpResponse {

    @ApiModelProperty(value = "电视墙列表")
    private List<TvWall> tvWalls;

}
