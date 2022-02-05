package com.example.juc.service;

import com.example.juc.util.SafeCollections;


import java.util.Map;
import java.util.concurrent.FutureTask;


public class JucService {

    public static void main(String[] args) {
        Map<String, String> map = SafeCollections.concurrentHashMap();
        map.put("a","b");
        System.out.println(map);
    }


}
