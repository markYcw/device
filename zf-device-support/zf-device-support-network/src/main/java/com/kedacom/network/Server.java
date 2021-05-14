package com.kedacom.network;

import java.io.IOException;

public class Server {
    public static void main(String[] args){

        String configIp = "127.0.0.1";
        int configPort = 18888;

        System.out.println(configIp+":"+configPort);

        SvrFrame svrFrame = new SvrFrame(configIp, configPort);


        try {
            svrFrame.InitIOSelector();
            svrFrame.InitSocket();
        } catch (IOException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx Init SERVER FAILED xxxxxxxxxxxxxxxxxxxxx");
        }

        svrFrame.start();
        System.out.println("xxxxxxxxxxxxxxxxxxx CHAT SERVER is running xxxxxxxxxxxxxxxxxxxxx");


    }
}
