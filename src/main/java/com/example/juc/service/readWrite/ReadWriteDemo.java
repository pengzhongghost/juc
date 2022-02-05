package com.example.juc.service.readWrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 写锁，一次只能被一个线程占用
 * 读锁，一次可以被多个线程占用
 */
public class ReadWriteDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp + "");
            }).start();
        }
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp + "");
            }).start();
        }
    }
}

//自定义缓存
class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //存，写
    public void put(String key, Object value) {
        Lock lock = readWriteLock.writeLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入缓存ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //取，读
    public void get(String key) {
        Lock lock = readWriteLock.readLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读");
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取缓存ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}