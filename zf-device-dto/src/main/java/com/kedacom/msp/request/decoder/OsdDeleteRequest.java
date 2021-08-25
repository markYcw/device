package com.kedacom.msp.request.decoder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 17:51
 */
@Data
@ApiModel(description =  "删除解码通道字幕信息入参")
public class OsdDeleteRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotBlank(message = "解码通道ID不能为空")
    @ApiModelProperty(value = "解码通道ID - 必填，统一设备服务分配国标ID", required = true)
    private String chnid;

    @ApiModelProperty(value = "画面编号集合， 编号从0开始")
    private List<Integer> osdls;

}
