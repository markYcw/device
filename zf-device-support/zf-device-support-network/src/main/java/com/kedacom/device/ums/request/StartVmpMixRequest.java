package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.requestdto.UmsScheduleGroupStartVmpMixAttendMembersRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupStartVmpMixSetStyleRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class StartVmpMixRequest extends BaseRequest {

    private static final String COMMAND = "startvmpmix";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "默认0-自动风格，>0,定制的画面布局，取值见创建会议 时的布局说明")
    private Integer layout;

    @ApiModelProperty(value = "是否广播（1:广播；0:不广播）")
    private Integer broadcast;

    @ApiModelProperty(value = "风格设置")
    private UmsScheduleGroupStartVmpMixSetStyleRequestDto style;

    @ApiModelProperty(value = "参与画面合成者设置")
    private List<UmsScheduleGroupStartVmpMixAttendMembersRequestDto> members;

    @ApiModelProperty(value = "观看画面合成的成员设备id列表")
    private List<String> listeners;

    @Override
    public String name() {
        return COMMAND;
    }

}
