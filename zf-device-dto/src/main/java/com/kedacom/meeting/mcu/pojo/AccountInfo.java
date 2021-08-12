package com.kedacom.meeting.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author hxj
 * @Date: 2021/8/12 16:44
 * @Description 账户信息
 */
@Data
@ApiModel(value = "账户信息")
public class AccountInfo implements Serializable {

    @ApiModelProperty(value = "账号启用停用标识:0-停用;1-启用")
    private Integer enable;

    @ApiModelProperty(value = "账号真实姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String passWord;

    @ApiModelProperty(value = "邮箱\n" +
            "1.字符限制：仅支持 英文字母（大小写）、数字、“_”、“@”、“.”、“-”")
    private String email;

    @ApiModelProperty(value = "手机\n" +
            "1.字符限制：仅支持 数字、“.”、“_”、“-”、“*”、“#”、空格")
    private String mobile;

    @ApiModelProperty(value = "默认为自动分配\n" +
            "0-不绑定号码；\n" +
            "1-手动分配；\n" +
            "2-自动分配；")
    private Integer bind;

    @ApiModelProperty(value = "e164号码（只有bind值为1时，才需要填值，其他情况不用填）")
    private Integer e164;

    @ApiModelProperty(value = "int\t性别 默认为1；0-女；1-男；")
    private Integer sex;

    @ApiModelProperty(value = "出生日期 格式为ISO8601:20:00标准")
    private String birth;

    @ApiModelProperty(value = "电话号分机号如：025-5555522-2189电话：025-5555522；分机：2189\n" +
            "1.字符限制：数字、（、）、，、-、；、*、+、#、空格、")
    private String phone;

    @ApiModelProperty(value = "传真；1.字符限制：数字、“-”")
    private String fax;

    @ApiModelProperty(value = "办公地址")
    private String officeLoc;

    @ApiModelProperty(value = "部门信息列表")
    private List<DepartmentInfo> departments;

    @ApiModelProperty(value = "账号编号/工号\n" +
            "1.字符限制：数字、字母")
    private String jobNum;

    @ApiModelProperty(value = "是否为来宾用户,默认为0\n" +
            "0-否；\n" +
            "1-是；")
    private Integer limited;

}
