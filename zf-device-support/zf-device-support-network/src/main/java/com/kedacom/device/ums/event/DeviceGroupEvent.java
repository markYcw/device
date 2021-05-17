package com.kedacom.device.ums.event;

import com.kedacom.core.pojo.NotifyHead;
import com.kedacom.device.ums.DeviceGroupVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Setter
@Getter
@ToString(callSuper = true)
public class DeviceGroupEvent extends ApplicationEvent {

    private NotifyHead nty;

    @ApiModelProperty(value = "总数")
    private Integer count;

    @ApiModelProperty(value = "是否结束")
    private Integer end;

    private List<DeviceGroupVo> result;

    public DeviceGroupEvent(Object source) {
        super(source);
    }
}
