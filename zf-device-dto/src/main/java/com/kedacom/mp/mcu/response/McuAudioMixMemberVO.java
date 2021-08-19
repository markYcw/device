package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.MtInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/19 14:50
 * @description mcu添加/删除混音成员响应
 */
@Data
@ApiModel(value = "mcu添加/删除混音成员响应")
public class McuAudioMixMemberVO implements Serializable {

    @ApiModelProperty(value = "终端")
    private List<MtInfo> mtInfos;

}
