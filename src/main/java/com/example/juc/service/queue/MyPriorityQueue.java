package com.example.juc.service.queue;

import java.util.PriorityQueue;

public class MyPriorityQueue {
    public static void main(String[] args) {
        PriorityQueue<User> queue = new PriorityQueue<>();
        queue.offer(new User(4));
        queue.offer(new User(6));
        queue.offer(new User(2));
        queue.offer(new User(5));
        queue.offer(new User(7));
        System.out.println(queue);
        while (!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }
}

class User implements Comparable<User> {

    private int id;

    public User(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }

    @Override
    public int compareTo(User user) {
        return this.id - user.id;
    }
}