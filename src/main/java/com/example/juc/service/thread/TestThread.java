package com.example.juc.service.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestThread {
    public static void main(String[] args) throws Exception {
        //todo 百分百有cpu执行权
        Thread.sleep(3);
        //todo jdk1.5之前调的是Thread.sleep(0)
        // 失去cpu执行权，从运行状态返回到就绪状态
        // cpu可能忽略此请求，只是一个提示的请求
        Thread.yield();
        yieldTest();
        //todo 中断线程的阻塞状态,给线程打一个中断的标记
        interruptTest();
        //todo 通过join获取cpu执行权
        joinTest();
        //todo park unPark 停止唤醒线程
        parkTest();

    }

    private static void parkTest() throws Exception{
        Thread thread = new Thread(()->{
            System.out.println("start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("park");
            LockSupport.park();
            System.out.println("resume");
        });
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        LockSupport.unpark(thread);
    }


    private static void joinTest() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "*" + i);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "*" + i);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("main");
        //TODO 线程生命周期结束再调join会阻塞，会等待自己结束,其实已经结束了
        //Thread.currentThread().join();
    }

    private static void interruptTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                //如果已经是中断状态，，sleep，wait，则抛出异常之后中断状态会被抹去isInterrupted还是false
                //TimeUnit.SECONDS.sleep(3);
                while (true) {
                    //获取是否中断的静态方法
                    //System.out.println(Thread.interrupted());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(100);
        //获取线程的中断标记
        System.out.println(thread.isInterrupted());//false
        thread.interrupt();
        System.out.println(thread.isInterrupted());//true
    }

    public static void yieldTest() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                if (finalI == 0)
                    Thread.yield();
                System.out.println(finalI);
            }).start();
        }
    }

}
