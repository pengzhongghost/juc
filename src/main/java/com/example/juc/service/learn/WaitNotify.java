package com.example.juc.service.learn;

public class WaitNotify {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "D").start();
    }
}

//wait notify
class Data {

    private int number = 0;

    public synchronized void increment() throws InterruptedException {
        //if判断会造成虚假判断，使用while就行，等待总是出现在循环中
        while (number != 0) {
            this.wait();
        }
        number++;
        //通知其他线程，我加1了
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        //if判断会造成虚假判断，使用while就行，等待总是出现在循环中
        while (number == 0) {
            this.wait();
        }
        number--;
        //通知其他线程，我-1了
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        this.notifyAll();
    }
}