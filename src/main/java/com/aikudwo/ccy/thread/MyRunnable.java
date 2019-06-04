package com.aikudwo.ccy.thread;

/**
 * @author wls
 * @date 2019-05-31 14:35
 * Runnable方式创建线程
 */
public class MyRunnable implements Runnable{
    private String name;
    public MyRunnable(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i <10 ; i++) {
            System.out.println("curent thread is :"+ Thread.currentThread().getName() +
                    "myName : " + this.name + "count :" + i);
        }
    }
}
