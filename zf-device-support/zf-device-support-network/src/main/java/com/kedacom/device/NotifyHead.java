package com.kedacom.device;

import lombok.*;

/**
 * 响应头
 * @author van.shu
 * @create 2021/5/13 20:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NotifyHead {

    private String name;

    private Integer ssno;

    private Integer ssid;
}
