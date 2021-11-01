package com.kedacom.cu.vo;

import com.kedacom.cu.pojo.Domains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取域链表响应体
 * @author ycw
 * @version v1.0
 * @date 2021/11/1 13:53
 * @description
 */
@Data
public class DomainsVo {

    @ApiModelProperty("域列表")
    private List<Domains> domains;

}
