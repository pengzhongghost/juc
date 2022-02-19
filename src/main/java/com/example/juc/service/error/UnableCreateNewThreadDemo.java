package com.example.juc.service.error;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Oom:unable to create new native thread 创建线程太多了
 * Linux系统默认运行单个进程可以创建的线程数双1024个
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger();
        while (true) {
            new Thread(() -> {
                try {
                    System.out.println(i.getAndIncrement());
                    Thread.sleep(Integer.MIN_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
