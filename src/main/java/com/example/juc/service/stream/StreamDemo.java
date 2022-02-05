package com.example.juc.service.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 1.id必须是偶数
 * 2.年龄必须大于20
 * 3.用户名转为大写字母
 * 4.用户名字母倒着排序
 * 5.只输出一个用户
 */
public class StreamDemo {
    public static void main(String[] args) {
        User user01 = new User(1, "a", 18);
        User user02 = new User(2, "b", 19);
        User user03 = new User(3, "c", 20);
        User user04 = new User(4, "d", 21);
        User user05 = new User(5, "e", 22);
        User user06 = new User(6, "f", 23);
        //集合就是存储
        List<User> list = Arrays.asList(user01, user02, user03, user04, user05, user06);
        //计算就是流
        //链式编程
        list.stream()
                .filter(user -> user.getId() % 2 == 0)
                .filter(user -> user.getAge() > 20)
                .map(user -> user.getName().toUpperCase())
                .sorted((u1, u2) -> u2.compareTo(u1))
                .limit(1)
                .forEach(user -> System.out.println(user));

        list.stream().sorted(Comparator.comparing(User::getId, Comparator.nullsLast(Integer::compareTo)).reversed()).skip(1).limit(5).collect(Collectors.toList());
        list.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getName))), ArrayList::new));
    }
}
