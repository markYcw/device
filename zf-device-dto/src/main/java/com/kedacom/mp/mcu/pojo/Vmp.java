package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @date: 2021/09/24 10:51
 * @description 画面合成设置
 */
@Data
@ApiModel(description =  "画面合成设置")
public class Vmp implements Serializable {

    @NotNull(message = "画面合成模式不能为空")
    @ApiModelProperty(value = "画面合成模式 1-定制画面合成；2-自动画面合成",required = true)
    private Integer mode;

    @ApiModelProperty(value = "画面合成风格: 1-一画面全屏;  2-两画面: 2等大，居中(1行2列);  3-两画面: 1大1小，1大全屏，1小右下;  61-两画面: 1大1小，1大全屏，1小右上;" +
            "62-两画面: 1大1小，1大全屏，1小左上; 63-两画面:  1大1小，1大全屏，1小左下;  23-三画面: 等大，1左，2右(2行1列);  4-三画面: 等大，1上2下; 5-四画面: 等大，2行2列;" +
            "35-五画面: 1大4小，1大上，4小下(1行4列);  6-六画面: 1大5小，1大左上，2小右上(2行1列)，3小下(1行3列);  13-七画面: 3大4小，2大上(1行2列)，1大左下，4小右下(2行2列);" +
            "7-八画面: 1大7小，1大左上，3小右上(3行1列)，4小下(1行4列);  8-九画面: 等大，3行3列;  18-十画面:2大8小，4小上(1行4列)，2大中(1行2列)，4小下(1行4列);" +
            "38-十一画面: 1大10小，1大上，10小下(2行5列);  39-十二画面: 3大9小，2大上(1行2列)，1大左下，9小右下(3行3列);  19-十三画面: 1大12小，4小上(1行4列)，2小左中(2行1列)，1大中中，2小右中(2行1列), 4小下(1行4列);" +
            "17-十四画面: 2大12小，2大左上(1行2列)，2小右上(2行1列)，10小下(2行5列); 20-十五画面:  3大12小，3大上(1行3列)，12小下(2行6列); 11-十六画面: 16等分，4x4;" +
            "46-十七画面: 1大16小，1大左上，6小右上(3行2列)，10小下(2行5列); 48-十八画面:  6大12小，6小上(1行6列)，6大居中(2行3列)，6小下(1行6列); " +
            "51-十九画面: 2大17小，2大左上(1行2列)，2小右上(2行1列)，15小下(3行5列);  14-二十画面: 2大18小，2大上(1行2列)，18小下(3行6列); " +
            "54-二十一画面: 1大20小，6小上(1行6列)，4小左中(4行1列)，1大中中，4小右中(4行1列)，6小下(1行6列); 56-二十二画面: 1大21小，1大左上，6小右上(2行3列)，15小下(3行5列);" +
            "59-二十四画面: 4大20小，6小上(1行6列)，4小左中(4行1列)，4大中中(2行2列)，4小右中(4行1列)，6小下(1行6列)； 27-二十五画面: 等大，5行5列;",required = true)
    @NotNull(message = "画面合成风格不能为空")
    private Integer layout;

    @NotNull(message = "是否识别声音来源不能为空")
    @ApiModelProperty(value = "是否识别声音来源0-否；1-是；",required = true)
    private Integer voiceHint;

    @NotNull(message = "是否向终端广播不能为空")
    @ApiModelProperty(value = "是否向终端广播 0-否；1-是；",required = true)
    private Integer broadCast;

    @NotNull(message = "是否显示别名不能为空")
    @ApiModelProperty(value = "是否显示别名 0-否；1-是；",required = true)
    private Integer showMtName;

    @NotNull(message = "画面合成参数不能为空")
    @ApiModelProperty(value = "画面合成参数",required = true)
    private MtNameStyle mtNameStyle;

    @NotEmpty(message = "画面合成成员列表不能为空")
    @ApiModelProperty(value = "画面合成成员列表")
    private List<Members> members;



}
