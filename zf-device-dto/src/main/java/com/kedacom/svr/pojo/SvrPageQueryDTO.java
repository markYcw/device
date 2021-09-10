package com.kedacom.svr.pojo;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author svr
 * @Date: 2021/09/06 10:48
 * @Description svr信息分页查询
 */
@Data
@ApiModel(description =  "svr信息分页查询")
public class SvrPageQueryDTO extends BasePage implements Serializable {

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("name")
    private String name;

}
