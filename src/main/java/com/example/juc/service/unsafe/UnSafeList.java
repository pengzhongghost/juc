package com.example.juc.service.unsafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class UnSafeList {

    public static void main(String[] args) {
        UnSafeList unSafeList = new UnSafeList();
        unSafeList.copyOnWrite();
    }

    //写入时复制一份新的数组
    public void copyOnWrite(){
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        common(list);
    }

    public void synchronizedList(){
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        common(list);
    }


    //java.util.ConcurrentModificationException  并发修改异常
    public void unSafe() {
        List<String> list = new ArrayList<>();
        common(list);
    }

    private void common(List<String> list) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }).start();
        }
    }

}
