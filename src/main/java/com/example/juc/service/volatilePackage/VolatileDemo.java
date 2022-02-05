package com.example.juc.service.volatilePackage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {
    //volatile
    //1,保证可见性     线程A见到主内存中的值发生了变化
    //2,不保证原子性
    //  不可分割  线程A在执行任务的时候，不能被打扰，也不能被分割，要么同时成功，要么同时失败
    //3,禁止指令重排

    //JMM:java内存模型，不存在的东西，概念，约定

    //    关于JMM的一些同步约定：
//    1.线程解锁前，必须把共享变量立刻刷回主存
//    2.线程加锁前，必须读取主存中的最新值到工作内存中
//    3.加锁和解锁是同一把锁
    private volatile static int num = 0;

    private volatile static AtomicInteger number = new AtomicInteger();

    //1,保证可见性
    public void couldSee() throws Exception {
        new Thread(() -> {
            while (num == 0) {
                //System.out.println(Thread.currentThread().getName());
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        num = 1;
        System.out.println(num);
    }

    //理论上应该为2万
    public void atomic() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    number.getAndIncrement();
                }
            }).start();
        }
    }

    public static void main(String[] args) throws Exception {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.atomic();

        //礼让
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(number);
    }


}
