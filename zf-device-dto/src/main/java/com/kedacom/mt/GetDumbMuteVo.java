package com.kedacom.mt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/2
 */
@Data
public class GetDumbMuteVo implements Serializable {

    @ApiModelProperty("True:静音控制" + "Fase:哑音控制")
    private String mute;

    @ApiModelProperty("静音/哑音 开关")
    private String open;
}
