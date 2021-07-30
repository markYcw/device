package com.kedacom.terminal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/7/30 10:50
 * @Description 终端的统一请求
 */
@Data
@ApiModel("终端的统一请求")
public class TerminalRequest implements Serializable {

    @ApiModelProperty(value = "终端的数据库id")
    private Long terminalId;

}
