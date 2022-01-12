package com.kedacom.device.core.ping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class DefaultPing implements IPing{
    @Override
    public boolean isAlive(PingInfo pingInfo) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        String pingCommand = "ping " + pingInfo.getIp() + " -c "
                + pingInfo.getTimes() + " -w " + pingInfo.getTimeout();
        try {
            log.info("isAlive: {} ", pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));    //  逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }    //  如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            log.info("ping sucess times : {}", connectedCount);
            return connectedCount == pingInfo.getTimes();
        } catch (Exception ex) {
            ex.printStackTrace();    //  出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getCheckResult(String line){   //  System.out.println("控制台输出的结果为:"+line);
//        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("(ttl=\\d+)(\\s+)(\\d+||(\\d+\\.\\d+)ms)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    public  static  void main(String[] args)  throws Exception {
//        PingInfo pingInfo= new PingInfo("172.16.231.43");
//        System.out.println(pingInfo);
//        boolean alive = new DefaultPing().isAlive(pingInfo);
//        System.out.println(alive);

        String line = "64 bytes from 172.16.231.43: icmp_seq=1 ttl=64 time=0.502 ms\n";
        int checkResult = getCheckResult(line);
        System.out.println(checkResult);
    }

}
