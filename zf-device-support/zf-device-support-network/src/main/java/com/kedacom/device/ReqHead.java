package com.kedacom.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author van.shu
 * @create 2021/5/13 14:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ReqHead {

    private String name;

    private Integer ssid;

    private Integer ssno;

}
