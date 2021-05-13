package com.kedacom.device;

import lombok.*;

/**
 * @author van.shu
 * @create 2021/5/13 14:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RespHead {

    private String name;

    private int ssno;

    private int ssid;

    private int errcode;

}
