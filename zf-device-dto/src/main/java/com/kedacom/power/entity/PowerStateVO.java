package com.kedacom.power.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date 2022/6/8 16:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerStateVO implements Serializable {

    private List<Integer> ids;

    private Integer state;

}
