package com.kedacom.mp.mcu.pojo;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 10:48
 * @Description 会议平台信息分页查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "会议平台信息分页查询")
public class McuPageQueryDTO extends BasePage implements Serializable {

    /**
     * 会议平台IP
     */
    @ApiModelProperty(value = "会议平台IP")
    private String ip;
    /**
     * 会议平台名称
     */
    @ApiModelProperty(value = "会议平台名称")
    private String name;

}
