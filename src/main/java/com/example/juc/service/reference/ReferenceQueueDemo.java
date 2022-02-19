package com.example.juc.service.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueueDemo {
    public static void main(String[] args) {
        Object o = new Object();
        ReferenceQueue referenceQueue = new ReferenceQueue();
        WeakReference weakReference = new WeakReference<>(o, referenceQueue);
        System.out.println(o);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());//null
        o = null;
        System.gc();
        try {
            //停0。5秒保证gc调用
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
    }
}
