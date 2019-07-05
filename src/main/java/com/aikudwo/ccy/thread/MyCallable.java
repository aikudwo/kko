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

    public static void main(String[] args) {
        int n = 7;
        Integer feibolaqi = new MyCallable().feibolaqi(n);
        System.out.println(feibolaqi);

    }

    private Integer feibolaqi(int n){
        int sum1 = 1;
        int sum2 = 1;
        int tem = 0;
        int sum = 0 ;
        for (int i = 0; i < n; i++) {
            if(n == 1 || n == 2){
                return sum1;
            }
            if(i>2){
                tem = sum2;
                sum2 = sum1 + sum2;
                sum1 = tem;
                sum = sum1 + sum2;

            }
        }
        return sum;
    }
}
