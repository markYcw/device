package com.kedacom.device.core.entity;


/**
 * @author van.shu
 * @create 2020/9/2 9:21
 */

public class UmsPair<K, V> {

    private K k;

    private V v;

    public UmsPair(K k, V v) {

        this.k = k;
        this.v = v;

    }

    public K getK() {
        return this.k;
    }

    public V getV() {
        return this.v;
    }
}
