package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.TvWall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:43
 * @description 获取电视墙列表入参
 */
@Data
@ApiModel(value = "获取电视墙列表响应")
public class McuTvWallsVO implements Serializable {

    @ApiModelProperty(value = "电视墙列表")
    private List<TvWall> tvWalls;

}
