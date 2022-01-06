package com.kedacom.svr.pojo;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author svr
 * @Date: 2021/09/06 10:48
 * @Description svr设备类型信息分页查询
 */
@Data
@ApiModel(description =  "svr信息分页查询")
public class SvrTypePageQueryDTO extends BasePage implements Serializable {

    @ApiModelProperty("设备类型")
    private Integer type;

}
