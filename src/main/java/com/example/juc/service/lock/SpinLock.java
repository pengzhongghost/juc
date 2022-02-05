package com.example.juc.service.lock;

import java.util.concurrent.atomic.AtomicReference;

//自旋锁
public class SpinLock {

    private AtomicReference atomicReference = new AtomicReference();

    //加锁
    public void myLock() {
        System.out.println(Thread.currentThread().getName() + "==> myLock");
        //自旋锁
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {

        }
    }

    //解锁
    public void myUnLock() {
        System.out.println(Thread.currentThread().getName() + "==> myUnLock");
        atomicReference.compareAndSet(Thread.currentThread(), null);
    }
}
