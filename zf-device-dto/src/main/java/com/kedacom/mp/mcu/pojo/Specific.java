package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:31
 * @description 选看参数
 */
@Data
@ApiModel(value = "选看参数")
public class Specific implements Serializable {

    @ApiModelProperty(value = "选看类型\n" +
            "1-指定；\n" +
            "2-发言人跟随；\n" +
            "3-主席跟随；\n" +
            "4-会议轮询跟随；\n" +
            "6-选看画面合成；\n" +
            "7-批量轮询；\n" +
            "10-选看双流；")
    private String memberType;

    @ApiModelProperty(value = "选看终端id，仅member_type为 1-指定 时生效 最大字符长度：48个字节")
    private String mtId;

    @ApiModelProperty(value = "选看画面合成id，仅member_type为 6-选看画面合成 时生效 最大字符长度：48个字节")
    private String vmpId;

}
