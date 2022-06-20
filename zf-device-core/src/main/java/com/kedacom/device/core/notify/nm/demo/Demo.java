package com.kedacom.device.core.notify.nm.demo;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/5/20 13:04
 * @description
 */
public class Demo {
    public static void main(String[] args) {
        People people1 = new People(1, "john", "keda", 0, "hello");
        People people2 = new People(1, "ma", "keda", 0, "hello");
        People people3 = new People(2, "j", "keda", 0, "hello");
        People people4 = new People(1, "o", "zhi", 0, "hello");
        People people5 = new People(1, "n", "hen", 0, "hello");
        People people6 = new People(1, "n", "rong", 0, "hello");
        People people7 = new People(1, "s", "keda", 0, "hello");

        List<People> list = Collections.synchronizedList(new ArrayList<People>());
        list.add(people1);
        list.add(people2);
        list.add(people3);
        list.add(people4);
        list.add(people5);
        list.add(people6);
        list.add(people7);

        String company = "keda";
        Integer age = 1;
        Integer a = 2;
        Stream<People> stream = list.stream();
        if(StringUtils.isNotBlank(company)){
            stream = stream.filter(x -> x.getCompany().equals(company));
        }
            stream = stream.filter(x->(x.getAge()==age || x.getAge()==a));
        List<People> collect = stream.collect(Collectors.toList());
        System.out.println("list:"+collect);

    }
}
