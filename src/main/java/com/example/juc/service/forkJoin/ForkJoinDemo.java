package com.example.juc.service.forkJoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo extends RecursiveTask<Long> {
    //工作窃取，不让线程等待
    //A线程执行任务没有完成，B线程执行任务完毕了，B线程回去获取A的任务执行
    //维护的是双端队列
    private Long start;
    private Long end;
    private Long temp = 10000L;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - start) < temp) {
            //正常计算
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //分支合并计算
            //计算中间值
            long middle = (start + end) / 2;
            //拆成两个任务,把任务压入队列
            ForkJoinDemo fork01 = new ForkJoinDemo(start, middle);
            fork01.fork();
            ForkJoinDemo fork02 = new ForkJoinDemo(middle + 1, end);
            fork02.fork();
            return fork01.join() + fork02.join();
        }
    }

}
