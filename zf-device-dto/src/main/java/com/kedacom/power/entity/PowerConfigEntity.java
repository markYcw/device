package com.kedacom.power.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 电源配置信息表
 * </p>
 *
 * @author zhanglongfei
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("km_power_config")
@Builder
public class PowerConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电源配置数据库Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备监听端口号
     */
    private Integer port;


}
