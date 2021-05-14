package com.kedacom.device;

import com.alibaba.fastjson.JSON;
import com.kedacom.core.pojo.RespHead;
import com.kedacom.device.ums.request.LoginRequest;
import com.kedacom.device.ums.response.LoginResponse;

/**
 * @author van.shu
 * @create 2021/5/13 14:58
 */
public class Test {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            LoginRequest request = new LoginRequest();

            request.setDevtype(i);

            request.setDevplatip("127");

            String json = request.buildData(i);


            System.out.println("json = " + json);

            int ssid = request.getSsid();
            System.out.println("ssid = " + ssid);
            int ssno = request.getSsno();
            System.out.println("ssno = " + ssno);

            System.out.println("-----------------------");
        }


        String json = loginRespStr();

        LoginResponse response = new LoginResponse();



    }

    private static String loginRespStr() {

        LoginResponse response = new LoginResponse();

        RespHead head = RespHead.builder()
                .ssid(1)
                .ssno(1)
                .name("777")
                .errcode(555)
                .build();

        response.setResp(head);

        //response.setId("999");
        //response.setIo(999);

        String json = JSON.toJSONString(response);

        System.out.println("json = " + json);

        return json;


    }
}
