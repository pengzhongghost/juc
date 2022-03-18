package com.example.juc.service.readWrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class 锁降级 {
    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        //todo 锁降级可以写锁变读锁
//        writeLock.lock();
//        System.out.println("write");
//        readLock.lock();
//        System.out.println("read");
        //todo 不可以读锁变写锁
        readLock.lock();
        System.out.println("read");
        writeLock.lock();
        System.out.println("write");
    }
}
