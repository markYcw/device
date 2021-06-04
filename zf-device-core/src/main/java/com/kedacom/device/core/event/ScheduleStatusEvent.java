package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@KmNotify(name = "groupstatusnty")
public class ScheduleStatusEvent implements Notify {

    @ApiModelProperty(value = "调度组唯一标识")
    private String GroupID;

    @ApiModelProperty(value = "设备ID")
    private String DeviceID;

    @ApiModelProperty(value = "状态码")
    private Integer Status;

    @ApiModelProperty(value = "当Status为0时，此字段将详细解释不在线原因；3表示繁忙，6表示离线，默认值为0。")
    private Integer CallFailureCode;

    @ApiModelProperty(value = "静音状态，1-静音，0-停止静音")
    private Integer SilenceState;

    @ApiModelProperty(value = "哑音状态，1-哑音，0-停止哑音")
    private Integer MuteState;

    @ApiModelProperty(value = "流媒体NMediaID")
    private Integer NMediaID;

    @ApiModelProperty(value = "话权变更类型 start-获取话权 stop-释放话权默认为空字符串")
    private String SpeakerAction;

    @ApiModelProperty(value = "话权变更方，群组内的设备ID，默认为空字符串")
    private String SpeakerID;

    @ApiModelProperty(value = "话权变更群组ID")
    private String RemoteID;

    @ApiModelProperty(value = "非必要字段，\"NMedia\"表示是新媒体的会，\"MCU\"表示会议平台的会，不填默认为新媒体的会")
    private String GroupSourceType;

    @ApiModelProperty(value = "设备资源ID")
    private String ResourceID;

    @ApiModelProperty(value = "设备双流资源ID")
    private String dualResourceID;

    @ApiModelProperty(value = "设备所有资源节点")
    private MediaResourceNode MediaResourceNodeInfo;

    @ApiModelProperty(value = "设备所有资源节点")
    private RealMediaResourceNode RealMediaResourceNodeInfo;

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
