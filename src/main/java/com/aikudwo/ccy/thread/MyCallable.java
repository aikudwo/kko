package com.aikudwo.ccy.thread;

import java.util.concurrent.Callable;

/**
 * @author wls
 * @date 2019-05-31 15:12
 *  Callable 方式创建线程
 */
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        String value = "ready to work";
        Thread.currentThread().sleep(5000);
        System.out.println("myCallable task is done ...");
        return value;
    }
}
