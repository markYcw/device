package com.kedacom.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 发号器
 * @author van.shu
 * @create 2021/5/11 16:57
 */
public class NumGen {

    private static final AtomicInteger NEXT_SN_CYCLIC_COUNTER = new AtomicInteger(0);

    private NumGen() {

    }

    /**
     * 获取流水号
     * @return 流水号
     */
    public static int getNum() {

        int next;

        for (;;) {
            int current = NEXT_SN_CYCLIC_COUNTER.get();

            if (current == Integer.MAX_VALUE) {
                next = 1;
            } else {
                next = (current + 1);
            }

            if (NEXT_SN_CYCLIC_COUNTER.compareAndSet(current, next)) {
                return next;
            }

        }

    }



}
