package com.example.juc.service.unsafe;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 方案1.
 *
 */
public class UnSafeSet {

    public static void main(String[] args) {
        UnSafeSet unSafeSet = new UnSafeSet();
        //方案1.Set<String> set = Collections.synchronizedSet(new HashSet<>());
        //方案2.CopyOnWriteArraySet
        unSafeSet.copyOnWrite();
    }

    public void copyOnWrite(){
        Set<String> set = new CopyOnWriteArraySet<>();
        common(set);
    }

    public void hsSet(){
        Set<String> set = new HashSet<>();
        common(set);
    }

    private void common(Set<String> set) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }).start();
        }
    }

}
