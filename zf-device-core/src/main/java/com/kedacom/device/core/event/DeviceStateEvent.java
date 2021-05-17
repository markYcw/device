package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Setter
@Getter
@ToString(callSuper = true)
@KmNotify(name = "devgroupstatusnty")
public class DeviceStateEvent extends ApplicationEvent implements Notify {

    @ApiModelProperty(value = "分组ID")
    private String id;

    @ApiModelProperty(value = "父分组ID")
    private String parentId;

    @ApiModelProperty(value = "分组名")
    private String name;

    @ApiModelProperty(value = "分组类型：0-新媒体分组，1-自定义分组")
    private Integer type;

    @ApiModelProperty(value = "分组目录")
    private String groupCatalog;

    @ApiModelProperty(value = "分组码")
    private Integer groupCode;

    @ApiModelProperty(value = "排序索引")
    private Integer sortIndex;

    @ApiModelProperty(value = "操作类型 10:分组增加修改，11:分组删除")
    private Integer operateType;

    public DeviceStateEvent(Object source) {
        super(source);
    }

    @Override
    public Integer acquireSsno() {
        return null;
    }

    @Override
    public String acquireCommand() {
        return null;
    }

    @Override
    public <T> T acquireData(Class<T> clazz) throws JSONException {
        return null;
    }

}
