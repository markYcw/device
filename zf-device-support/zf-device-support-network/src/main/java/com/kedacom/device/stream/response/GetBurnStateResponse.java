package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 15:06
 * @description
 */
@Data
@ApiModel(description = "刻录状态请求响应")
@ToString(callSuper = true)
public class GetBurnStateResponse extends BaseResponse {

    @ApiModelProperty("通道号，目前一般为0，合成通道")
    private Integer ChnId;

    @ApiModelProperty("消息类型 0:未知 1:刻录状态 2:刻录进度 3:刻录错误 4:刻录全部完成 5:追加刻录电子笔录完成 6:刻录全状态,此时所有成员均有效")
    private Integer MsgType;

    @ApiModelProperty("状态，当MsgType为1时有效 0:空闲状态， 1:正在刻录 2:刻录完成 3:正在停止刻录中 4:正在补刻/重新刻录状态中 5:正在追加刻录 6:正在刻录平台录像")
    private Integer Status;

    @ApiModelProperty("光盘总的空间(M),蓝光最大支持50G,网络序")
    private Integer TotalSpace;

    @ApiModelProperty("光盘剩余空间(M),网络序")
    private Integer RemainingSpace;

    @ApiModelProperty("错误码")
    private Integer ErrorCode;

    @ApiModelProperty("DVD编号")
    private Integer DvdId;


}
