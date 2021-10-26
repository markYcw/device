package com.kedacom.deviceListener;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 注册URL用于监听设备通知
 * @author ycw
 * @create 2021/06/19 14:47
 */
@Data
public class RegisterListenerVo {

    @ApiModelProperty("数据库ID")
    private Integer id;

    @ApiModelProperty("方法路径")
    private String url;

    @ApiModelProperty("通知类型：详情见MsgType类")
    private String msgType;


    @ApiModelProperty("本次注册错误码 10010:重复注册 1:成功")
    private Integer error;

}
