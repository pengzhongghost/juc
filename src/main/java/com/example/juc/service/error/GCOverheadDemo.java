package com.example.juc.service.error;

import java.util.ArrayList;

/**
 * Oom: GC overhead limit exceeded
 * 大部分的内存或者超过98%的时间用来做gc内存回收并且回收了不到2%的内存。
 */
public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        ArrayList<String> list = new ArrayList<>();
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println("===" + i);
            e.printStackTrace();
        }
    }
}
