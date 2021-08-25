package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "开始画面合成请求参数类")
public class UmsScheduleGroupStartVmpMixRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "默认0-自动风格，>0,定制的画面布局，取值见创建会议 时的布局说明")
    private Integer layout;

    @NotNull(message = "是否广播参数不能为空")
    @ApiModelProperty(value = "是否广播（1:广播；0:不广播）")
    private Integer broadcast;

    @ApiModelProperty(value = "风格设置")
    private UmsScheduleGroupStartVmpMixSetStyleRequestDto style;

    @NotEmpty(message = "参与画面合成设备不能为空")
    @ApiModelProperty(value = "参与画面合成者设置")
    private List<UmsScheduleGroupStartVmpMixAttendMembersRequestDto> members;

    @ApiModelProperty(value = "观看画面合成的成员设备id列表")
    private List<String> listeners;

}
