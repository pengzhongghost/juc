package com.example.juc.service.deadlock;


import java.util.concurrent.TimeUnit;

public class Demo {
    private static final Integer lock01 = 1;
    private static final Integer lock02 = 2;

    public static void main(String[] args) {
        new Thread(() -> {
            test01();
        }).start();
        new Thread(() -> {
            test02();
        }).start();
    }


    public static void test01() {
        synchronized (lock01) {
            System.out.println(lock01);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock02) {
                System.out.println(lock02);
            }
        }
    }

    public static void test02() {
        synchronized (lock02) {
            System.out.println(lock02);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock01) {
                System.out.println(lock01);
            }
        }
    }
}
