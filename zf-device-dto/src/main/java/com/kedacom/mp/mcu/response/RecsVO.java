package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.Rec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @date: 2021/10/19 10:25
 * @description 获取录像列表
 */
@Data
@ApiModel(description =  "获取录像列表")
public class RecsVO implements Serializable {

    @ApiModelProperty("录像列表")
    private List<Rec> recs;



}
