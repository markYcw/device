package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "参与画面合成的成员设备信息(id为设备国标id-name不填)")
public class UmsScheduleGroupStartVmpMixAttendMembersRequestDto implements Serializable {

    @ApiModelProperty(value = "参与画面合成者的设备国标id")
    private String id;

    @ApiModelProperty(value = "参与画面合成者的名称")
    private String name;

    @ApiModelProperty(value = "该合成者在画面合成中的位置")
    private Integer index;

    @ApiModelProperty(value = "对应窗口的背景显示图（0-不显示；1、会议终端；2、执法记录仪；3、单兵；4、IPC；5、人像卡口；6、视信通；7、手机；8、固话；9、350M；10、通用类型）默认为0，1080p、layout=1时，图片大小为960 540，居中显示，会根据layout等比例缩放）")
    private Integer backgroundPic;

}
