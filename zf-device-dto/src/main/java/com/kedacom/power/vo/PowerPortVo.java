package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PowerPortVo
 * @Description 监听端口CRUD参数类
 * @Author zlf
 * @Date 2021/5/25 16:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "监听端口CRUD参数类")
public class PowerPortVo implements Serializable {

    @NotEmpty(message = "数据库Id集合不能为空")
    @ApiModelProperty(value = "数据库Id集合", required = true)
    private List<Integer> ids;
}
