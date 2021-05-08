package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 09:56
 */
@Data
@ApiModel("任意开窗入参")
public class TvPlayOpenRequest implements Serializable {


    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty(message = "预案ID不能为空")
    @ApiModelProperty(value = "预案ID - 必填，预案的配置ID")
    private Integer schid;

    @NotEmpty(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所属的大屏ID")
    private Integer tvid;

    @ApiModelProperty(value = "窗口信息")
    private List<LayoutData> wnds;

    @Data
    class LayoutData {

        @ApiModelProperty(value = "窗口ID - 只读")
        private Integer wnd_id;

        @NotEmpty(message = "窗口行起始位置不能为空")
        @ApiModelProperty(value = "窗口行起始位置 - 必填，单位：像素")
        private Integer x;

        @NotEmpty(message = "窗口列起始位置不能为空")
        @ApiModelProperty(value = "窗口列起始位置 - 必填，单位：像素")
        private Integer y;

        @NotEmpty(message = "窗口宽度不能为空")
        @ApiModelProperty(value = "窗口宽度 - 必填，单位：像素")
        private Integer w;

        @NotEmpty(message = "窗口高度不能为空")
        @ApiModelProperty(value = "窗口高度 - 必填，单位：像素")
        private Integer h;

        @ApiModelProperty(value = "信号参数")
        private List<ChnInfo> chns;

    }

    @Data
    class ChnInfo {

        @NotEmpty(message = "信号源ID不能为空")
        @ApiModelProperty(value = "信号源ID - 必填，国标ID或资源ID")
        private String chnid;

        @NotEmpty(message = "信号源类型不能为空")
        @ApiModelProperty(value = "信号源类型 - 必填，0=监控源、1=画面合成源或者会议终端、2=调度资源（需通过第三方服务操作）")
        private Integer chntype;

        @ApiModelProperty(value = "信号源名称 - 信号源为资源ID时，需设置资源对应的名称，显示使用")
        private String chnname;

        @ApiModelProperty(value = "会议终端的国标ID - 注意老版本融合调度系统，只有是会议终端时有效，新版本融合调度系统该字段不用")
        private String chnidex;

        @NotEmpty(message = "小窗口索引不能为空")
        @ApiModelProperty(value = "小窗口索引 - 必填，四画面时，信号源显示在小窗口的位置")
        private Integer index;

        @ApiModelProperty(value = "包传输方式 - 0=单播，1=组播，默认单播")
        private Integer protocol;

        @ApiModelProperty(value = "组播地址信息")
        private List<Multicast> multicast;

    }

    @Data
    class Multicast {

        @ApiModelProperty(value = "组播地址")
        private String ip;

        @ApiModelProperty(value = "组播端口")
        private Integer port;

    }


}
