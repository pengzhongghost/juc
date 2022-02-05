package com.example.juc.service.queue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class QueueDemo {
    //阻塞队列
    //写入，如果队列满了，就必须阻塞等待
    //读取，如果队列空了，也必须阻塞等待
    public static void main(String[] args) throws InterruptedException {
        QueueDemo queueDemo = new QueueDemo();
        queueDemo.test05();
    }
    /**
     * 同步队列
     */
    public void test05(){
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" put a");
                queue.put("a");
                System.out.println(Thread.currentThread().getName()+" put b");
                queue.put("b");
                System.out.println(Thread.currentThread().getName()+" put c");
                queue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+queue.take());
                System.out.println(Thread.currentThread().getName()+queue.take());
                System.out.println(Thread.currentThread().getName()+queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * 阻塞队列
     */
    //抛出异常
    public void test01() {
        //队列的大小
        Queue queue = new ArrayBlockingQueue<>(3);
        System.out.println(queue.add("a"));
        System.out.println(queue.add("b"));
        System.out.println(queue.add("c"));
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        //java.util.NoSuchElementException 队列已经空了
        System.out.println(queue.remove());
        //java.lang.IllegalStateException  队列已经满了
        System.out.println(queue.add("d"));
    }

    //不抛出异常
    public void test02() {
        //队列的大小
        Queue queue = new ArrayBlockingQueue<>(3);
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        //返回false
        System.out.println(queue.offer("d"));
        //检测队首元素
        System.out.println(queue.element());
        System.out.println(queue.peek());
        //poll 移除元素
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        //null 队列中无值了
        System.out.println(queue.poll());
    }

    //等待，阻塞（一直阻塞）
    public void test03() throws InterruptedException {
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(3);
        queue.put("a");
        queue.put("b");
        queue.put("c");
        //取出
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        queue.put("d");
    }


    //等待，阻塞（超时退出）
    public void test04() throws InterruptedException {
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(3);
        queue.offer("a", 2, TimeUnit.SECONDS);
        queue.offer("b", 2, TimeUnit.SECONDS);
        queue.offer("c", 2, TimeUnit.SECONDS);
        //如果队列是满的，则超时退出了
        queue.offer("d", 2, TimeUnit.SECONDS);
        System.out.println("结束"+queue);
        queue.poll( 2, TimeUnit.SECONDS);
        queue.poll( 2, TimeUnit.SECONDS);
        queue.poll( 2, TimeUnit.SECONDS);
        Object poll = queue.poll(2, TimeUnit.SECONDS);
        System.out.println(poll);
    }

}
