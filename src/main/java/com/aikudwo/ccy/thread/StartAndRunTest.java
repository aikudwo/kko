package com.aikudwo.ccy.thread;

/**
 * @author wls
 * @date 2019-05-31 14:04
 * start() 和 run() 操作验证
 */
public class StartAndRunTest {
    private static void attack(){
        System.out.println("fight");
        System.out.println("current Thread" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
         Thread t = new Thread(){
             public void run(){
              attack();
             }
         };
        System.out.println("current main is thread is :" + Thread.currentThread().getName());
       // t.run();
        t.start();
    }

}
