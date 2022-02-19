package com.example.juc.service.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceDemo {
    public static void main(String[] args) {
        soft();
    }

    //软引用
    public static void soft() {
        Object o = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(softReference.get());
        o = null;
        System.gc();
        System.out.println(o);
        System.out.println(softReference.get());
    }

    //弱引用
    public static void weak() {
        Object o = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o);
        System.out.println(o);
        System.out.println(weakReference.get());
        o = null;
        System.gc();
        System.out.println(o);
        System.out.println(weakReference.get());
    }

}
