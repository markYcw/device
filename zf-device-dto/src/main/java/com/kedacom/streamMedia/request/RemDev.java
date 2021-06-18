package com.kedacom.streamMedia.request;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/6/18 08:58
 */
@Data
@ApiModel(value = "远程点设备信息")
public class RemDev implements Serializable {

    @JSONField(name = "Name")
    private String name;

    @JSONField(name = "Url")
    private String url;

}
