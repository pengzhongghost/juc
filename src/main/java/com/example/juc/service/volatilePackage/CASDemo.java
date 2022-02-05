package com.example.juc.service.volatilePackage;

import java.util.concurrent.atomic.AtomicStampedReference;

public class CASDemo {

    public static void main(String[] args) {

        //-128到127之外会创建新对象
        Integer num01 = 130;
        Integer num02 = new Integer(130);
        //false
        System.out.println(num01 == num02);

        System.out.println(Integer.MIN_VALUE);
        System.out.println(new Integer(129));
        //Integer对象缓存机制,泛形如果是包装类，注意对象的引用问题
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "|" + stamp);

            System.out.println(atomicStampedReference.compareAndSet(1, 2,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));

            System.out.println(atomicStampedReference.compareAndSet(2, 3,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
        }).start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "|" + stamp);

            System.out.println(atomicStampedReference.compareAndSet(1, 4,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
        }).start();

    }

}
