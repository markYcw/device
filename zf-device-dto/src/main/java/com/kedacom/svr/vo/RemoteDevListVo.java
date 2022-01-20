package com.kedacom.svr.vo;

import com.kedacom.svr.dto.SvrRequestDto;
import com.kedacom.svr.pojo.DevList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:01
 * @description 查询录像
 */
@Data
public class RemoteDevListVo extends SvrRequestDto {

    @ApiModelProperty(value = "远程点设备列表")
    private List<DevList> devList;

}
