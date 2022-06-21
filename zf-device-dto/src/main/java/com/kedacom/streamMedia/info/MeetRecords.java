package com.kedacom.streamMedia.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/21 14:57
 */
@Data
public class MeetRecords implements Serializable {

    @ApiModelProperty(value = "录像记录的id")
    private String ID;

    @ApiModelProperty(value = "摄像头通道信息")
    private String ChnId;

    @ApiModelProperty(value = "录像文件完整名称，即包含绝对路径")
    private String FileName;

    @ApiModelProperty(value = "开始时间")
    private Long Begin;

    @ApiModelProperty(value = "结束时间")
    private Long End;

    @ApiModelProperty(value = "文件起始时间（该字段只存在于本级录像）")
    private Long FileStartTime;

    @ApiModelProperty(value = "文件结束时间（该字段只存在于本级录像）")
    private Long FileEndTime;

    @ApiModelProperty(value = "录像文件大小，单位Kb")
    private Long Size;

    @ApiModelProperty(value = "录像文件存放的位置 \"LocalServer\"存放在本地服务端存储 \"Remote\" 存放在前端设备或其它域的服务器")
    private String RecordStorePosition;

    @ApiModelProperty(value = "录像文件hash值【特殊项目使用】")
    private String Hash;

    @ApiModelProperty(value = "录像文件下载地址，格式 http:ip:port/{FileName}。 该字段只有当录像管理服务中配置了下载服务器地址后才会有")
    private String Url;

}
