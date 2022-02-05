package com.example.juc.service.add;

import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.semaphore();
    }

    //信号量 限流
    public void semaphore() {
        //停车位
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                //得到车位 semaphore.acquire();
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放车位 semaphore.release();
                    semaphore.release();
                }
            }).start();
        }
        System.out.println("semaphore");
    }

    //加法计数器
    public void cyclicBarrier() {
        int num = 7;
        //集齐七颗龙珠召唤神龙
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, () -> {
            System.out.println("召唤神龙成功");
        });
        for (int i = 0; i < num; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "收集了第" + temp + "颗龙珠");
                try {
                    //等待计数器变为1
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }


    //并发计数器,一般是必须要执行的任务时使用
    public void countDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "同学走了");
                countDownLatch.countDown();
            }).start();
        }
        try {
            //等待计数器归零
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("关门");
    }


}
