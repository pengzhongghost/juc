package com.example.juc.service.locksupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Unpark就将许可证变为1发放许可证，park消费permit，将1变为0，重复调用uppark不可以累积，重复调用park而没有调用unpark会阻塞。
 * 1）Wait和notify不可以脱离synchroniezd
 * 先notify后wait无效，notify必须要在wait后面执行才能唤醒
 * wait是先要释放锁，notify是让一个线程获得锁。Notify先找到这个对象的锁，
 * 再从这个锁对象的等待队列中去唤醒一个线程，等待队列中没有线程无法唤醒。
 * 2）lock的await和signal和上面类似
 */
public class LockSupportDemo {

    static Object objectLock = new Object();

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---进入");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "---被唤醒");
        }, "A");
        a.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            LockSupport.unpark(a);
            System.out.println(Thread.currentThread().getName() + "---唤醒");

        }, "B").start();
    }

    public static void ConditionDemo() {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "---进入");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "---被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A").start();
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "---唤醒");
            } finally {
                lock.unlock();
            }
        }, "B").start();
    }

}
