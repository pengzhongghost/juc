package com.example.juc.service.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelayedQueue {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<WangMin> queue = new DelayQueue<>();
        new Thread(() -> {
            queue.offer(new WangMin(1, 3000 + System.currentTimeMillis()));
            queue.offer(new WangMin(2, 2000 + System.currentTimeMillis()));
            queue.offer(new WangMin(3, 5000 + System.currentTimeMillis()));
        }).start();
        while (true) {
            System.out.println(queue.take());
        }
    }

}

//网名
class WangMin implements Delayed {

    private int id;
    //下机时间
    private long endTime;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    public WangMin(int id, long endTime) {
        this.id = id;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "WangMin{" +
                "id=" + id +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(this.timeUnit) - o.getDelay(this.timeUnit));
    }
}