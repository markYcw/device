package com.kedacom.device.core.notify.cu.loadGroup.notify;

import com.kedacom.device.core.notify.cu.loadGroup.pojo.GetGroupNotify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/10 11:27
 * @description
 */
@ToString(callSuper = true)
@Data
public class GroupsNotify extends CuNotify{

    @ApiModelProperty("分组通知内容")
    private GetGroupNotify content;

}
