package com.aikudwo.ccy.thread;

/**
 * @author wls
 * @date 2019-05-31 14:28
 * Thread 方式创建线程
 */
public class MyThread extends Thread{
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i <10 ; i++) {
            System.out.println("curent thread is :"+ Thread.currentThread().getName() +
                    "myName : " + this.name + "count :" + i);
        }
    }
}
