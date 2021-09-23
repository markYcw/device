package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:18
 * @description 录像参数
 */
@Data
@ApiModel(description =  "录像参数")
public class RecParam implements Serializable {

    @NotNull(message = "录像类型不能为空")
    @ApiModelProperty(value = "录像类型:1-会议录像；2-终端录像；",required = true)
    private Integer recorderType;

    @ApiModelProperty(value = "录像保存的文件名称:1.字符限制：\n" +
            "  a.不支持输入特殊字符 % : & * ^ ~ ' \"\" ? / \\ < > | ` \" $\n" +
            "  b.首字符和尾字符，不允许输入下划线（_） 减号（-） @ 小数点（.）\n" +
            "（可输入在非首尾字符）\n" +
            "2.最大字符长度：64个字节",required = true)
    @NotBlank(message = "录像保存的文件名称会议号码不能为空")
    private String videoName;

    @NotNull(message = "发布模式不能为空")
    @ApiModelProperty(value = "发布模式:0-不发布；1-发布；",required = true)
    private Integer publishMode;

    @NotNull(message = "是否支持免登陆观看直播不能为空")
    @ApiModelProperty(value = "是否支持免登陆观看直播:0-不支持；1-支持；",required = true)
    private Integer anonymous;

    @NotNull(message = "是否录主格式码流不能为空")
    @ApiModelProperty(value = "是否录主格式码流（视频+音频）:0-否；1-是；",required = true)
    private Integer mainStream;

    @NotNull(message = "是否录双流不能为空")
    @ApiModelProperty(value = "是否录双流（仅双流）:0-否；1-是；",required = true)
    private Integer dualStream;

    @NotBlank(message = "vrs的id信息不能为空")
    @ApiModelProperty(value = "vrs的id信息,用于指定录像的vr",required = true)
    private String vrsId;

    @ApiModelProperty(value = "开启录像终端ID列表，当开启终端录像需要填写")
    private List<String> mtInfos;

}
