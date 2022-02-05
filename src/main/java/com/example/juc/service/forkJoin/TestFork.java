package com.example.juc.service.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestFork {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestFork testFork = new TestFork();
        //testFork.test01();
        testFork.test02();
        testFork.test03();
    }

    //普通程序员
    public void test01() {
        long start01 = System.currentTimeMillis();
        Long sum = sum(1L, 10_0000_0000L);
        long end01 = System.currentTimeMillis();
        System.out.println("sum=" + sum + "时间：" + (end01 - start01));
    }

    //forkjoin
    public void test02() throws ExecutionException, InterruptedException {
        long start01 = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask task = new ForkJoinDemo(1L, 10_0000_0000L);
        //执行任务
        //forkJoinPool.execute(task);
        //提交任务有结果
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        Long sum = submit.get();
        long end01 = System.currentTimeMillis();
        System.out.println("sum=" + sum + "时间：" + (end01 - start01));
    }

    //stream并行流
    public void test03() {
        long start01 = System.currentTimeMillis();

        long sum = LongStream.rangeClosed(1L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        long end01 = System.currentTimeMillis();
        System.out.println("sum=" + sum + "时间：" + (end01 - start01));
    }

    //求和计算的任务
    public Long sum(Long start, Long end) {
        Long sum = 0L;
        for (Long i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }
}
