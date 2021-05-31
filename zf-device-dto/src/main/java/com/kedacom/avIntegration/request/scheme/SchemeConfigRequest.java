package com.kedacom.avIntegration.request.scheme;

import com.kedacom.avIntegration.info.LayoutParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所在的大屏的ID", required = true)
    private Integer tvid;

    @ApiModelProperty(value = "预案ID - 调度测自定义的预案ID（例如三思拼接器场景），该值有效时，拼控不返回窗口ID和预案ID")
    private Integer schid;

    @NotBlank(message = "预案名称不能为空")
    @ApiModelProperty(value = "预案名称 - 必填，预案的显示名称，最大64个英文字符", required = true)
    private String name;

    @ApiModelProperty(value = "是否等分 - 等分是单元格合并；不等分填写窗口布局，默认等分")
    private boolean isequant;

    @ApiModelProperty(value = "是否保留屏上窗口 是否保留大屏上的窗口，默认不保留清屏，园区拍拍项目定制接口 add by 2021/05/06")
    private boolean iskeep;

    @ApiModelProperty(value = "等分布局下必填:单元格行数，预案画面虚拟切分小单元格行数")
    private Integer cell_row;

    @ApiModelProperty(value = "等分布局下必填:单元格列数，预案画面虚拟切分小单元格列数")
    private Integer cell_col;

    @ApiModelProperty(value = "窗口数组 - 等分模式下填写合并单元格的索引，不等分时填写坐标")
    private List<LayoutParam> layout;

}
