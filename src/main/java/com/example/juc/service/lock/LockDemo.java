package com.example.juc.service.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) {
        Phone2 phone2 = new Phone2();

        new Thread(() -> {
            phone2.call();
        }).start();
        new Thread(() -> {
            phone2.call();
        }).start();
    }

}

class Phone2 {

    private ReentrantLock lock = new ReentrantLock();

    public void call() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "   call");
        sendSms();//这里也有锁
        lock.unlock();
    }

    public void sendSms() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "   sendSms");
        lock.unlock();
    }

}

class Phone {

    public synchronized void call() {
        System.out.println(Thread.currentThread().getName() + "   call");
        sendSms();//这里也有锁
    }

    public synchronized void sendSms() {
        System.out.println(Thread.currentThread().getName() + "   sendSms");
    }

}