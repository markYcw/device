package com.kedacom.svr.vo;

import com.kedacom.svr.pojo.BurnStatesInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:49
 * @description
 */
@Data
public class BurnStatesInfoVo{

    @ApiModelProperty("当前刻录信息")
    private BurnStatesInfo burnInfo;

}
