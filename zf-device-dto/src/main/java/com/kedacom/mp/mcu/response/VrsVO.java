package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.Vrs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @date: 2021/10/19 10:25
 * @description 获取Vrs列表
 */
@Data
@ApiModel(description =  "获取Vrs列表")
public class VrsVO implements Serializable {

    @ApiModelProperty("vrs列表")
    private List<Vrs> vrs;



}
