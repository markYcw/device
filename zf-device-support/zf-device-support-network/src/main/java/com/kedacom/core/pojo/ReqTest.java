package com.kedacom.core.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author van.shu
 * @create 2021/5/12 19:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqTest implements Serializable {

    private Req req;

   // private Integer GroupID;

   // private Teacher teacher;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class Req implements Serializable {

        private String name;

        private int ssno;

        private int ssid;

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class TnT {

        private int GroupID;

        private Teach teacher;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class Teach{

        private String name;

        private int age;

        private List<Student> userList;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class Student implements Serializable {

        private int id;

        private String name;

        private int age;

    }


    public static String get() {
        ReqTest reqTest = new ReqTest();
        Req req = new Req("ccc", 1, 1);
        reqTest.setReq(req);
        //reqTest.setGroupID(1);
        Teacher teacher = new Teacher();
        teacher.setName("老师");
        teacher.setAge(88);
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "1", 1));
        userList.add(new User(2, "2", 2));
        userList.add(new User(3, "3", 3));
        teacher.setUserList(userList);
        //reqTest.setTeacher(teacher);
        String json = JSON.toJSONString(reqTest);
        System.out.println("json = " + json);
        return json;

    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {


        String str = get();

        JSONObject jsonObject = JSONObject.parseObject(str);

        boolean containsReq = jsonObject.containsKey("req");

        System.out.println("containsReq = " + containsReq);

        boolean containsNotify = jsonObject.containsKey("notify");

        System.out.println("containsNotify = " + containsNotify);

        Object req = jsonObject.remove("req");

        System.out.println("req = " + req);

        System.out.println(jsonObject);

        Map map = jsonObject.toJavaObject(Map.class);

        System.out.println("map = " + map);

        Set set = map.keySet();

        for (Object o : set) {
            System.out.println("o = " + o);
            String keyType = o.getClass().getTypeName();
            System.out.println("keyType = " + keyType);
            Object value = map.get(o);
            System.out.println("value = " + value);
            String valueTypeName = value.getClass().getTypeName();
            System.out.println("valueTypeName = " + valueTypeName);
        }

        String dataStr = JSON.toJSONString(map);

        TnT tnT = JSON.parseObject(dataStr, TnT.class);

        System.out.println("tnT = " + tnT);


    }

}
