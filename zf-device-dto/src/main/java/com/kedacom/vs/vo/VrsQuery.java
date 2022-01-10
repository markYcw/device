package com.kedacom.vs.vo;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * VRS分页查询载体
 * @author ycw
 * @create 2021/05/14 17:42
 */
@Data
public class VrsQuery extends BasePage {

    @ApiModelProperty("VRS名称")
    private String name;

    @ApiModelProperty("VRS的ip")
    private String ip;
}
