package com.kedacom.acl.network.data.avIntegration.precall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 15:18
 */
@Data
@ApiModel("获取预加载任务详细信息入参")
public class PrecallQueryResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败-1")
    private Integer error;

    @ApiModelProperty("失败时信息")
    private String message;

    @ApiModelProperty("预加载任务详细信息")
    private List<PreCallInfo> preCallInfo;

    @Data
    class PreCallInfo {

        @ApiModelProperty("任务ID")
        private Integer preCallID;

        @ApiModelProperty("任务名称")
        private String plan_name;

        @ApiModelProperty("起始时间，格式YYYY-MM-DDThh:mm:ss")
        private String start_time;

        @ApiModelProperty("结束时间，格式YYYY-MM-DDThh:mm:ss")
        private String end_time;

        @NotEmpty
        @ApiModelProperty("信号源ID列表")
        private List<String> deviceIDs;

    }

}
