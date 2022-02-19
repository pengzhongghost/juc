package com.example.juc.service.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {
    //todo 虚引用一般和ReferenceQueue一起使用，在gc之后放入引用队列之中
    public static void main(String[] args) {
        Object o = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o, referenceQueue);
        System.out.println(o);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
        System.out.println("=========");
        o = null;
        System.gc();
        try {
            //停0。5秒保证gc调用
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
