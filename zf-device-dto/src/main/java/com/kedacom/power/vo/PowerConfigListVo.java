package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerConfigListVo
 * @Description 条件获取电源配置设备信息参数类
 * @Author zlf
 * @Date 2021/5/26 18:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "条件获取电源配置设备信息参数类")
public class PowerConfigListVo implements Serializable {

    /**
     * 电源配置数据库Id
     */
    @ApiModelProperty(value = "电源配置数据库Id")
    private Integer id;

}
