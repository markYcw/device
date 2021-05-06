package com.kedacom.avIntegration.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:55
 */
@Data
@ApiModel("设置预案布局应答-窗口参数")
public class LayoutData implements Serializable {

    @ApiModelProperty(value = "窗口ID - 画面对应的窗口ID，窗口显示和关闭显示时所需的窗口控制ID")
    private Integer wnd_id;

}
