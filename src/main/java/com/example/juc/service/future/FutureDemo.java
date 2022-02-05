package com.example.juc.service.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureDemo demo = new FutureDemo();
        demo.all();
    }

    /**
     * CompletableFuture一般往里面传自己创建的线程池
     */
    //没有返回值的异步回掉
    public void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "runAsync=>void");
        });
        System.out.println("11111111111");
        //获取执行结果
        future.get();
    }


    public void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            int i = 0 / 0;
            System.out.println(Thread.currentThread().getName() + "supplyAsync=>Integer");
            return 1024;
        });
        Integer index = completableFuture.whenComplete((t, u) -> {
            //t是正常的返回结果
            System.out.println(t);
            //u是错误的信息
            System.out.println(u);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 233;
        }).get();
        System.out.println("结果：" + index);
    }

    public void handle() throws ExecutionException, InterruptedException {
        //whenComplete只可以感知结果不能处理
        CompletableFuture.supplyAsync(() -> {
            int i = 0 / 0;
            System.out.println(Thread.currentThread().getName() + "supplyAsync=>Integer");
            return 1024;
        }).whenComplete((res, thr) -> {
            if (res != null) {
                System.out.println(res);
            }
            if (thr != null) {
                System.out.println(thr);
            }
        });
        //handle感知结果并修改结果
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            int i = 0 / 0;
            System.out.println(Thread.currentThread().getName() + "supplyAsync=>Integer");
            return 1024;
        }).handle((res, thr) -> {
            if (res != null) {
                return res * 10;
            }
            if (thr != null) {
                System.out.println(thr);
                return 0;
            }
            return 0;
        });
        Integer res = completableFuture.get();
        System.out.println(res);
    }

    //接收上一步的返回值
    public void then() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> 5).thenRunAsync(() -> {
            System.out.println("等待上一步执行完毕");
        });
        //没有返回值
        CompletableFuture.supplyAsync(() -> 5).thenAcceptAsync(System.out::println);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5).thenApplyAsync(res -> {
            System.out.println(res);
            return res + 5;
        });
        System.out.println(future.get());
    }

    //合并两个异步任务
    public void combine() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future01 =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName());
                    return 5;
                });
        CompletableFuture<Integer> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return 15;
        });
        //两个都执行完毕执行自己的，不能感知到之前的执行结果
        //future01.runAfterBoth(future02,()-> System.out.println("任务执行完毕"));
        //可以获取之前执行的结果
//        CompletableFuture<Void> voidCompletableFuture = future01.thenAcceptBoth(future02, (res01, res02) -> {
//            System.out.println(res01 + res02);
//        });
        //可以获取之前执行的结果,并可返回结果
        CompletableFuture<Integer> future = future01.thenCombine(future02, (res01, res02) -> {
            System.out.println(res01 + res02);
            return res01 + res02;
        });
        System.out.println(future.get());
    }

    //两个任务只要有一个完成，就执行任务3
    public void either() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return 5;
        });
        CompletableFuture<Integer> future02 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            return 15;
        });
        //future01,future02只要有一个执行完这个就会执行
        //CompletableFuture<Void> voidCompletableFuture = future01.runAfterEitherAsync(future02, () -> System.out.println("任务3"));
        //感知到的结果不一定,自己没有返回值
//        CompletableFuture<Void> voidCompletableFuture = future01.acceptEitherAsync(future02,
//                res -> System.out.println("任务3：" + res));
        ///感知到的结果不一定,自己也有返回值
        CompletableFuture<Integer> future = future01.applyToEitherAsync(future02, res -> {
            System.out.println("任务3：" + res);
            return res + 1;
        });
        System.out.println(future.get());
        System.out.println(future02.get());
    }

    //多任务组合
    public void all() throws ExecutionException, InterruptedException {
        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("查询商品的图片信息");
            return "hello.jpg";
        });
        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的属性");
            return "黑色";
        });
        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的介绍");
            return "华为";
        });
        //等待所有结果全部完成
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(futureImg, futureAttr, futureDesc);
//        voidCompletableFuture.get();
//        //获取他们的内容
//        System.out.println(futureImg.get() + futureAttr.get() + futureDesc.get());
        //等待其中的任何一个执行完成
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(futureImg, futureAttr, futureDesc);
        System.out.println(objectCompletableFuture.get());

    }

}
