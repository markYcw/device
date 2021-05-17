package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@ToString(callSuper = true)
@Data
@ApiModel("停止音频混音业务交互参数")
public class StopAudioMixRequest extends BaseRequest {

    private static final String COMMAND = "stopaudiomix";

    @ApiModelProperty("音频混音设备组id")
    private String GroupID;

    @ApiModelProperty("画面合成ID集合")
    private List<String> mixIDs;

    @Override
    public String name() {
        return COMMAND;
    }

}
