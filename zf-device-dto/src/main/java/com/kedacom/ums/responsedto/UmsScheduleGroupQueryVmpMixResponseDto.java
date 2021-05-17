package com.kedacom.ums.responsedto;

import com.kedacom.ums.requestdto.UmsScheduleGroupStartVmpMixAttendMembersRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupStartVmpMixSetStyleRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "查询合成画面响应参数类")
public class UmsScheduleGroupQueryVmpMixResponseDto implements Serializable {

    @ApiModelProperty(value = "画面合成资源ID")
    private String vmpID;

    @ApiModelProperty(value = "默认0-自动风格，>0,定制的画面布局，取值见创建会议 时的布局说明")
    private Integer layout;

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
