package com.example.juc.service.example;

import java.util.concurrent.TimeUnit;

/**
 * 八锁
 *
 */
public class Test01 {
    public static void main(String[] args) {
        Phone4 phone4 = new Phone4();
        //锁的问题
        new Thread(() -> {
            phone4.sendSms();
        }).start();
        //sleep不会释放锁，抱着锁睡着了
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone4.call();
        }).start();

    }


}

class Phone1 {
    //synchronized 锁的对象是方法的调用者
    //两个方法用的是同一个锁，谁先调用谁就先执行
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendSms");
    }

    public synchronized void call() {
        System.out.println("call");
    }
}

class Phone2 {
    //synchronized 锁的对象是方法的调用者
    //两个方法用的是同一个锁，谁先调用谁就先执行
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendSms");
    }

    public synchronized void call() {
        System.out.println("call");
    }

    //hello方法上没有锁，谁先执行随机
    public void hello() {
        System.out.println("hello");
    }
}

class Phone3 {
    //static 是类一加载就有了，锁是类
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendSms");
    }
    public static synchronized void call() {
        System.out.println("call");
    }
}

class Phone4 {
    //static 是类一加载就有了，锁是类
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendSms");
    }
    public synchronized void call() {
        System.out.println("call");
    }
}