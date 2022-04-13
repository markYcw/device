package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PowerDeviceDeleteVo
 * @Description 删除电源设备信息参数类
 * @Author zlf
 * @Date 2021/5/27 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "删除电源设备信息参数类")
public class PowerDeviceDeleteVo implements Serializable {

    /**
     * 电源设备数据库Id
     */
    @NotEmpty(message = "电源设备数据库Id集合不能为空")
    @ApiModelProperty(value = "电源设备数据库Id集合", required = true)
    private List<Integer> ids;

}