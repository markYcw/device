package com.kedacom.aiBox.request;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/15
 */
@Data
@ApiModel(description =  "分页查询AI-Box设备信息列表请求参数类")
public class SelectPageRequestDto extends BasePage implements Serializable {

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "设备名称")
    private String name;

}
