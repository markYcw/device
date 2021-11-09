package com.kedacom.cu.dto;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author cu
 * @Date: 2021/10/29 16:48
 * @Description cu信息分页查询
 */
@Data
@ApiModel(description =  "cu信息分页查询")
public class DevEntityQuery extends BasePage implements Serializable {

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("name")
    private String name;

}
