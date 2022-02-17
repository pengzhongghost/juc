package com.example.juc.service.volatilePackage;

import java.util.concurrent.TimeUnit;

public class VolatileDemo2 {

    public int num = 0;

    public volatile static int count = 0;

    public void addNum60() {
        num = 60;
        count = 60;
    }

    public static void main(String[] args) {
        test02();
    }

    public static void test01() {
        VolatileDemo2 demo2 = new VolatileDemo2();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo2.addNum60();
            System.out.println(Thread.currentThread().getName() + "\t go");
        }, "AAA").start();
        while (demo2.num == 0) {
        }
        System.out.println(Thread.currentThread().getName() + "\t finish");
    }

    public static void test02() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VolatileDemo2.count = 60;
            System.out.println(Thread.currentThread().getName() + "\t go");
        }, "AAA").start();
        while (VolatileDemo2.count == 0) {
        }
        System.out.println(Thread.currentThread().getName() + "\t finish");
    }

}
