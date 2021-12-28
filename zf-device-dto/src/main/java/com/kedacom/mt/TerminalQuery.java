package com.kedacom.mt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/30
 */
@Data
public class TerminalQuery {

    @ApiModelProperty("当前页")
    private int page;

    @ApiModelProperty("每页记录数")
    private int limit;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("设备IP")
    private String ip;

}
