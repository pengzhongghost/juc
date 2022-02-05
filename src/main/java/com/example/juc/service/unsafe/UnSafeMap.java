package com.example.juc.service.unsafe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UnSafeMap {

    public static void main(String[] args) {
        UnSafeMap unSafeMap = new UnSafeMap();
        unSafeMap.concurrent();
    }

    public void concurrent(){
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        common(map);
    }

    public void hashMap() {
        Map<String, String> map = new HashMap<>();
        common(map);
    }

    public void common(Map map) {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }).start();
        }
    }


}
