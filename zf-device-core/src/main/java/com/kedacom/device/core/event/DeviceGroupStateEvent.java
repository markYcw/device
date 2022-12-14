package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import com.kedacom.core.pojo.NotifyHead;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "devgroupstatusnty")
public class DeviceGroupStateEvent implements Notify {

    private NotifyHead nty;

    @ApiModelProperty(value = "分组ID")
    private String id;

    @ApiModelProperty(value = "父分组ID")
    private String parentId;

    @ApiModelProperty(value = "分组名")
    private String name;

    @ApiModelProperty(value = "分组类型：0-新媒体分组，1-自定义分组")
    private String type;

    @ApiModelProperty(value = "分组目录")
    private String groupCatalog;

    @ApiModelProperty(value = "分组码")
    private Integer groupCode;

    @ApiModelProperty(value = "排序索引")
    private Integer sortIndex;

    @ApiModelProperty(value = "操作类型 10:分组增加修改，11:分组删除")
    private Integer operateType;

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
