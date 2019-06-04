package com.aikudwo.ccy.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author wls
 * @date 2019-06-04 15:12
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        //只能5个线程同时访问
        Semaphore semaphore = new Semaphore(5);
        //模拟20个客户访问
        for (int i = 0; i < 20 ; i++) {
            final int NO = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    //获得许可
                    try {
                        semaphore.acquire();
                        System.out.println("Accessing: " + NO);
                        Thread.sleep((50000));
                        //访问完。释放
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(runnable);
        }
    }
}
