package com.example.juc.service.volatilePackage;

import java.util.concurrent.TimeUnit;

public class VolatileDemo2 {

    public volatile int num = 0;

    public volatile static int count = 0;

    public void addNum60() {
        num = 60;
        count = 60;
    }

    public void incrNum() {
        num++;
    }

    public static void main(String[] args) {
        test03();
    }

    //todo 1.可见性
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

    //todo 1.可见性
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

    //todo 2.不保证原子性
    public static void test03() {
        VolatileDemo2 demo2 = new VolatileDemo2();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    demo2.incrNum();
                }
            }, String.valueOf(i)).start();
        }
        //活跃线程数大于2，主线程和gc线程占两个
        while (Thread.activeCount() > 2) {
            //放弃cpu执行权，礼让其他线程
            Thread.yield();
        }
        System.out.println(demo2.num);
    }


}
