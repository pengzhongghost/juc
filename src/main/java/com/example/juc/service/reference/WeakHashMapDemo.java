package com.example.juc.service.reference;

import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {
        myWeakHashMap();
    }

    public static void myHashMap() {
        String key = "1";
        HashMap<Object, Object> map = new HashMap<>();
        map.put(key, "nihao");
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map);
    }

    public static void myWeakHashMap() {
        //必须要是new创建的对象的weakHashmap的key被置为空之后gc之后才有可能被删除
        String key = new String("3");
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        map.put(key, "nihao");
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map);
    }

}
