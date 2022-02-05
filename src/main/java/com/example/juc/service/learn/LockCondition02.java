package com.example.juc.service.learn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//condition实现有序执行
public class LockCondition02 {
    public static void main(String[] args) {
        Data03 data03 = new Data03();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data03.print01();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data03.print02();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data03.print03();
            }
        }, "C").start();
    }
}

class Data03 {

    private Lock lock = new ReentrantLock();

    private Condition condition01 = lock.newCondition();
    private Condition condition02 = lock.newCondition();
    private Condition condition03 = lock.newCondition();

    private int num = 0;

    public void print01() {
        lock.lock();
        try {
            while (num != 0) {
                condition01.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>AAAAA");
            //唤醒指定的线程
            num = 1;
            condition02.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void print02() {
        lock.lock();
        try {
            while (num != 1) {
                condition02.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>BBBBB");
            //唤醒指定的线程
            num = 2;
            condition03.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void print03() {
        lock.lock();
        try {
            while (num != 2) {
                condition03.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>CCCCC");
            //唤醒指定的线程
            num = 0;
            condition01.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

}