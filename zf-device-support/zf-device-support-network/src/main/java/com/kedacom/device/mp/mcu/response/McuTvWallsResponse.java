package com.kedacom.device.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.TvWall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:43
 * @description 获取电视墙列表中间件响应
 */
@Data
@ApiModel(value = "获取电视墙列表中间件响应")
public class McuTvWallsResponse implements Serializable {

    @ApiModelProperty(value = "电视墙列表")
    private List<TvWall> tvWalls;

}
