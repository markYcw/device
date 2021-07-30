package com.kedacom.msp.request.virtual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 18:20
 */
@Data
@ApiModel("录入大屏列表入参")
public class VirtualTvWallRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @ApiModelProperty("选填 设备id，可通过pcsmc工具查询到")
    private Integer devid;

    @ApiModelProperty("大屏列表")
    private List<Tv> tvs;

    @Data
    class Tv{

        @ApiModelProperty("大屏在设备上的私有id 选填")
        private Integer id;

        @NotEmpty
        @ApiModelProperty("大屏在设备上的名称 必填")
        private String name;

        @NotNull
        @ApiModelProperty("大屏分辨率宽 必填")
        private Integer res_width;

        @NotNull
        @ApiModelProperty("大屏分辨率高 必填")
        private Integer res_height;

        @NotNull
        @ApiModelProperty("大屏布局，行数 必填")
        private Integer cell_row;

        @NotNull
        @ApiModelProperty("大屏布局，列数 必填")
        private Integer cell_col;

        @NotNull
        @ApiModelProperty("是否规则等分布局 必填")
        private Boolean isequant;

        @NotNull
        @ApiModelProperty("是否可以编辑 必填")
        private Boolean isedit;
    }

}
