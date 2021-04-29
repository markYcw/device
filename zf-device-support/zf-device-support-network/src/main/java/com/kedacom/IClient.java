package com.kedacom;

/**
 * @author van.shu
 * @create 2021/4/26 16:03
 */
public interface IClient<E, V> {


    V send(E request);

}
