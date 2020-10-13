package com.wangyuming.current;

import java.util.Date;
import java.util.concurrent.*;

public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {

        // 队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(10);

        // 初始化线程池执行器
        ExecutorService pool = new ThreadPoolExecutor(2, 10, 4000, TimeUnit.MILLISECONDS, workQueue);

        for (int i = 0; i < 20; i++) {

            // 提交任务
            pool.submit(new MyTask());
        }
    }

}

class MyTask implements Runnable {

    public void run() {
        System.out.println(new Date() + "  " + Thread.currentThread().getName() + " is working");
        try {
            // 模拟一段耗时工作
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(new Date() + "  " + Thread.currentThread().getName() + " done");
    }
}
