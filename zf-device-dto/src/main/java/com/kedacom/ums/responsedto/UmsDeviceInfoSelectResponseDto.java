package com.kedacom.ums.responsedto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Data
@ApiModel(description =  "查询统一设备响应参数类")
public class UmsDeviceInfoSelectResponseDto implements Serializable {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "设备名称，必填")
    private String name;

    @ApiModelProperty(value = "设备类型, 统一设备为28")
    private String deviceType;

    @ApiModelProperty(value = "统一设备IP地址")
    private String deviceIp;

    @ApiModelProperty(value = "统一设备端口")
    private Integer devicePort;

    @ApiModelProperty(value = "设备状态订阅IP")
    private String deviceNotifyIp;

    @NotNull(message = "设备状态订阅信息不能为空")
    private String kafkaAddr;

    @ApiModelProperty(value = "媒体调度服务IP")
    private String mediaIp;

    @ApiModelProperty(value = "媒体调度服务端口")
    private Integer mediaPort;

    @ApiModelProperty(value = "流媒体服务IP")
    private String streamingMediaIp;

    @ApiModelProperty(value = "流媒体服务端口")
    private Integer streamingMediaPort;

    @ApiModelProperty(value = "录像服务端口")
    private Integer streamingMediaRecPort;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "拼控服务账号")
    private String mspAccount;

    @ApiModelProperty(value = "拼控服务密码")
    private String mspPassword;

    @ApiModelProperty(value = "接口地址前缀",required = true)
    private String preAddress;

    @ApiModelProperty(value = "EX服务IP")
    private String exIp;

    @ApiModelProperty(value = "EX服务端口")
    private Integer exPort;

}
