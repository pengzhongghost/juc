package com.example.juc.service.functioninterface;

import com.alibaba.druid.util.StringUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//函数式接口
public class FunctionInterfaceDemo {

    public static void main(String[] args) {
        //        @FunctionalInterface
        //        public interface Runnable {
        //            public abstract void run();
        //        }
        //foreach 是consumer
        FunctionInterfaceDemo demo = new FunctionInterfaceDemo();
        demo.supplier();

    }

    //供应型接口
    public void supplier() {
        Supplier<String> supplier = () -> "hello";
        System.out.println(supplier.get());
    }

    //消费型接口
    public void consumer() {
        Consumer<String> consumer = str-> System.out.println(str);
        consumer.accept("hello");
    }

    //断定型接口
    //判断字符串是否为空
    public void predicate() {
        Predicate<String> predicate = str -> StringUtils.isEmpty(str);
        System.out.println(predicate.test(""));
    }

    //函数型接口
    public void function() {
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        };
        Function<String, String> function02 = (s) -> {
            return s;
        };
        Function<String, String> function03 = s -> {
            return s;
        };

        System.out.println(function03.apply("hello"));
    }

}
