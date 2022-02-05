package com.example.juc.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class SafeCollections {

    public static <V> List<V> copyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    public static <V> Set<V> copyOnWriteArraySet() {
        return new CopyOnWriteArraySet();
    }

    public static <K, V> Map<K, V> concurrentHashMap() {
        return new ConcurrentHashMap();
    }

}
