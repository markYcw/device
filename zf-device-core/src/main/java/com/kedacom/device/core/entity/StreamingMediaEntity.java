package com.kedacom.device.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
@Data
@TableName("ums_streaming_media")
public class StreamingMediaEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String smName;

    @ApiModelProperty(value = "IP")
    private String smIp;

    @ApiModelProperty(value = "端口")
    @Min(value = 0, message = "端口号参数不正确")
    @Max(value = 65536, message = "端口号参数不正确")
    private Integer smPort;

}
