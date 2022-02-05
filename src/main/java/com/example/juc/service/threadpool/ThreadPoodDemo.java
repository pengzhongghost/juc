package com.example.juc.service.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoodDemo {
    //线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式
    //    newFixedThreadPool和newSingleThreadExecutor:
    //    主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
    //    newCachedThreadPool和newScheduledThreadPool:
    //    主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。

    public void learn() {
        Executors.newSingleThreadExecutor();//单个线程
        Executors.newFixedThreadPool(5);//创建一个固定的线程池大小
        Executors.newCachedThreadPool();//可伸缩的，遇强则强，遇弱则弱
        Executors.newScheduledThreadPool(5);
    }

    public static void main(String[] args) {
        ThreadPoodDemo threadPoodDemo = new ThreadPoodDemo();
        threadPoodDemo.cache();
    }

    public void cache(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public void fix() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public void single() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
