package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.responsedto.QueryMediaResponseDto;
import com.kedacom.ums.responsedto.UmsScheduleGroupQueryMediaResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/14
 */
@ToString(callSuper = true)
@Data
public class SetMediaRequest extends BaseRequest {

    private static final String COMMAND = "setscheduling";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "媒体源列表包括音频和视频")
    private List<QueryMediaResponseDto> Members;

    @Override
    public String name() {
        return COMMAND;
    }

}
