package com.kedacom.device.ums.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author van.shu
 * @create 2021/5/13 14:07
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
public class LoginRequest extends UmsRequest {

    private static final String COMMAND = "login";

    private Integer devtype;
    private String devplatip;
    private Integer devplatport;
    private String devnotifyip;
    private String mediascheduleip;
    private Integer mediascheduleport;
    private String nmediaip;
    private Integer nmediaport;
    private Integer recport;

    @Override
    public String getCommand() {
        return COMMAND;
    }

}