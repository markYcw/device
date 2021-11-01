package com.kedacom.cu.vo;
import com.kedacom.cu.pojo.ViewTrees;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 获取多视图设备树响应体
 * @author ycw
 * @version v1.0
 * @date 2021/11/1 13:53
 * @description
 */
@Data
public class ViewTreesVo {

    @ApiModelProperty("设备树列表")
    private List<ViewTrees> trees;

}
