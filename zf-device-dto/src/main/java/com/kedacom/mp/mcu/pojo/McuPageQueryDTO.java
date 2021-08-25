package com.kedacom.mp.mcu.pojo;

import com.kedacom.BasePage;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 10:48
 * @Description 会议平台信息分页查询
 */
@Data
@ApiModel(description =  "会议平台信息分页查询")
public class McuPageQueryDTO extends BasePage implements Serializable {
}
