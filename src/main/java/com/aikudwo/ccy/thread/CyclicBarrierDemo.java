package com.aikudwo.ccy.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author wls
 * @date 2019-06-04 14:57
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        try {
            new CyclicBarrierDemo().go();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void go() throws InterruptedException {
        //初始化栅栏的参与者为3
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        new Thread(new Task(cyclicBarrier),"thread1").start();
        Thread.sleep(1000);
        new Thread(new Task(cyclicBarrier),"thread2").start();
        Thread.sleep(1000);
        new Thread(new Task(cyclicBarrier),"thread3").start();
        Thread.sleep(1000);
    }
    class Task implements Runnable{
        private CyclicBarrier cyclicBarrier;
        public Task(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier= cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName()+ "已经到达");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getName()+ "开始处理");
        }
    }
}
