package com.kedacom.device.ums.request;

import com.kedacom.acl.network.anno.KmJsonField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author van.shu
 * @create 2021/5/13 14:07
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
public class LoginRequest extends BaseRequest {

    private static final String COMMAND = "login";

    @KmJsonField(name = "devtype")
    @ApiModelProperty(value = "设备类型, 统一设备为28")
    private String deviceType;

    @KmJsonField(name = "devplatip")
    @ApiModelProperty(value = "统一设备IP地址")
    private String deviceIp;

    @KmJsonField(name = "devplatport")
    @ApiModelProperty(value = "统一设备端口")
    private Integer devicePort;

    @KmJsonField(name = "devnotifyaip")
    @ApiModelProperty(value = "设备状态订阅IP")
    private String deviceNotifyIp;

    @KmJsonField(name = "mediascheduleip")
    @ApiModelProperty(value = "媒体调度服务IP")
    private String mediaIp;

    @KmJsonField(name = "mediascheduleport")
    @ApiModelProperty(value = "媒体调度服务端口")
    private Integer mediaPort;

    @KmJsonField(name = "nmediaip")
    @ApiModelProperty(value = "流媒体服务IP")
    private String streamingMediaIp;

    @KmJsonField(name = "nmediaport")
    @ApiModelProperty(value = "流媒体服务端口")
    private Integer streamingMediaPort;

    @KmJsonField(name = "recport")
    @ApiModelProperty(value = "录像服务端口")
    private Integer streamingMediaRecPort;

    @Override
    public String name() {
        return COMMAND;
    }

}
