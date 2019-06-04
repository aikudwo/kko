package com.aikudwo.ccy.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author wls
 * @date 2019-06-04 14:40
 */
public class CountDownDemo {
    public static void main(String[] args) throws InterruptedException {
        new CountDownDemo().go();
        System.out.println();
    }

    private void go() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(new Task(countDownLatch),"thread1").start();
        Thread.sleep(100);
        new Thread(new Task(countDownLatch),"thread2").start();
        Thread.sleep(100);
        new Thread(new Task(countDownLatch),"thread3").start();
        Thread.sleep(100);
        countDownLatch.await();
        System.out.println("所有的线程已经到达，主线程开始执行" + System.currentTimeMillis());
    }
    class Task implements Runnable{
        private CountDownLatch countDownLatch;

        public Task (CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "  已经到到达");
            countDownLatch.countDown();
        }
    }
}
