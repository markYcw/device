package com.kedacom.avIntegration.request;

import com.kedacom.avIntegration.data.LayoutParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:46
 */
@Data
@ApiModel("设置预案布局入参")
public class SchemeConfigRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所在的大屏的ID")
    private Integer tvid;

    @ApiModelProperty(value = "预案ID - 调度测自定义的预案ID（例如三思拼接器场景），该值有效时，拼控不返回窗口ID和预案ID")
    private Integer schid;

    @NotEmpty(message = "预案名称不能为空")
    @ApiModelProperty(value = "预案名称 - 必填，预案的显示名称，最大64个英文字符")
    private String name;

    @ApiModelProperty(value = "是否等分 - 等分是单元格合并；不等分填写窗口布局，默认等分")
    private boolean isequant;

    @ApiModelProperty(value = "窗口数组 - 等分模式下填写合并单元格的索引，不等分时填写坐标")
    private List<LayoutParam> layout;

}
