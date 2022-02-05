package com.example.juc.service.threadpool;

import java.util.concurrent.*;

public class PoolExecutor {
    public static void main(String[] args) {
        //最大线程该如何定义
//cpu 密集型 几核cpu就是几个 可以保证CPU的效率最高
//IO  密集型 判断程序中十分耗IO的线程
        // 程序 15个大型任务 io十分占用资源 一般是两倍
        //获取cpu核数
        System.out.println(Runtime.getRuntime().availableProcessors());


//        public ThreadPoolExecutor(int corePoolSize, 核心线程池大小
//        int maximumPoolSize,最大核心线程池大小
//        long keepAliveTime,超时没有调用就会释放
//        TimeUnit unit,超时单位
//        BlockingQueue<Runnable> workQueue,阻塞队列
//        ThreadFactory threadFactory,线程工厂，创建线程的
//        RejectedExecutionHandler handler)拒绝策略
        PoolExecutor poolExecutor = new PoolExecutor();
        poolExecutor.discardOldestPolicy();

    }

    //拒绝策略，maximumPoolSize和workQueue都满了会尝试丢弃队列中最早的任务
    public void discardOldestPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                //银行满了，还有人进来，就不处理并抛出异常
                //maximumPoolSize和workQueue都满了会尝试丢弃队列中最早的任务,如果还在执行则终止
                //public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                //            if (!e.isShutdown()) {
                //                e.getQueue().poll();
                //                e.execute(r);
                //            }
                //        }
                new ThreadPoolExecutor.DiscardOldestPolicy());
        common(threadPoolExecutor);
    }

    //拒绝策略，maximumPoolSize和workQueue都满了不会抛出异常，会丢掉任务
    public void discardPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                //银行满了，还有人进来，就不处理并抛出异常
                //maximumPoolSize和workQueue都满了不会抛出异常，会丢掉任务
                new ThreadPoolExecutor.DiscardPolicy());
        common(threadPoolExecutor);
    }

    //拒绝策略，哪来的去哪里
    public void callerRunsPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                //银行满了，还有人进来，就不处理并抛出异常
                //maximumPoolSize和workQueue都满了则会哪里的去哪里，main线程执行
                new ThreadPoolExecutor.CallerRunsPolicy());
        common(threadPoolExecutor);
    }


    //拒绝策略maximumPoolSize和workQueue都满了则会抛出异常
    public void abortPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                //银行满了，还有人进来，就不处理并抛出异常
                //maximumPoolSize和workQueue都满了则会抛出异常
                //java.util.concurrent.RejectedExecutionException
                new ThreadPoolExecutor.AbortPolicy());
        common(threadPoolExecutor);
    }

    public void common(ThreadPoolExecutor threadPoolExecutor) {
        try {
            for (int i = 0; i < 100; i++) {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

}
