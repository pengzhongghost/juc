package com.example.juc.service.optional;

import com.example.juc.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class OptionalDemo {
    public static void main(String[] args) {
        User user = null;
        //判断一个对象是不是空的
        Optional<User> userOptional = Optional.ofNullable(user);
        //1.todo 安全的消费指 user不为null才会去执行
        userOptional.ifPresent(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user.getAge());
            }
        });
        //user不为null才会去执行
        userOptional.ifPresent(user1 -> System.out.println(user1.getAge()));
        //2.todo 安全的获取值
        //不为空则返回该值，为空则返回传入的值
        User get = userOptional.orElseGet(User::new);
        //3.todo 过滤
        get = userOptional.filter(u -> u.getAge() < 18).orElseGet(User::new);
        System.out.println(get);
//        user = new User();
//        user.setAge(18);
//        user.setName("你好");
//        //传入的为null会抛出异常
//        Optional<User> optionalUser = Optional.of(user);
        ArrayList<User> list = new ArrayList<>();
        User user3 = new User(1L, "test", 19);
        list.add(user3);
        List<User> students = new ArrayList<>();
        students.add(new User(3L, "test011", 18));
        user3.setStudents(students);
        Optional<List<User>> listOptional = Optional.ofNullable(list);
        boolean b = listOptional.orElseGet(ArrayList::new)
                .stream().anyMatch(user1 ->
                        Optional.ofNullable(user1.getStudents())
                                .orElseGet(ArrayList::new).stream()
                                .anyMatch(user2 -> user2.getAge() > 18));
        System.out.println(b);
    }
}
