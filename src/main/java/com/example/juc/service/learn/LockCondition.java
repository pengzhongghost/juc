package com.example.juc.service.learn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCondition {
    public static void main(String[] args) {
        Data02 data02 = new Data02();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data02.add();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data02.add();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data02.delete();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data02.delete();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "D").start();
    }
}

class Data02 {

    private int num = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void add() throws InterruptedException {
        lock.lock();
        while (num != 0) {
            condition.await();
        }
        num++;
        //通知其他线程，我加1了
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        condition.signalAll();
        lock.unlock();
    }

    public void delete() throws InterruptedException {
        lock.lock();
        while (num == 0) {
            condition.await();
        }
        num--;
        //通知其他线程，我减1了
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        condition.signalAll();
        lock.unlock();
    }

}