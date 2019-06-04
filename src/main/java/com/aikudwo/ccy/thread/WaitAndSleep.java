package com.aikudwo.ccy.thread;

/**
 * @author wls
 * @date 2019-05-31 17:18
 * 验证wait sleep
 */
public class WaitAndSleep {
    public static void main(String[] args) {
      final  Object lock = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("threa1获得锁");
                    try {
                        Thread.sleep(20);
                        System.out.println("threa1执行方法中.......");
                        lock.wait(20);
                        System.out.println("thread1 进行锁等待");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("threa2获得锁");
                    try {
                        Thread.sleep(10);
                        System.out.println("threa2方法执行中....");
                        lock.wait(20);
                        System.out.println("threa2执行完毕释放锁");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
