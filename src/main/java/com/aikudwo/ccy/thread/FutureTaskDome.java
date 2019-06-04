package com.aikudwo.ccy.thread;

import java.util.concurrent.FutureTask;

/**
 * @author wls
 * @date 2019-05-31 15:14
 * futureTask 方式获取线程返回值
 */
public class FutureTaskDome {
    public static void main(String[] args) throws Exception{
        FutureTask<String> ts = new FutureTask<>(new MyCallable());
        new Thread(ts).start();
        if(!ts.isDone()){
            System.out.println("working !!!");
        }
        System.out.println("task return----"+ ts.get() +"------good!!!");
    }
}
