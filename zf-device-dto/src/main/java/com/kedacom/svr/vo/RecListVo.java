package com.kedacom.svr.vo;

import com.kedacom.svr.pojo.RecVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 15:54
 * @description 查询录像
 */
@Data
public class RecListVo {

    @ApiModelProperty("录像列表")
    private List<RecVo> recList;
}
