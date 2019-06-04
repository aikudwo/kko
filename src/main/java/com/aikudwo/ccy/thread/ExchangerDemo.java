package com.aikudwo.ccy.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wls
 * @date 2019-06-04 16:59
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(() ->{
            //女生对男生说
            try {
                String girl = exchanger.exchange("我其实暗恋你很久了");
                System.out.println("女生说 ：" + girl);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //男生对女生说
                 String boy =   exchanger.exchange("我喜欢你..........");
                    System.out.println("男孩说 ：" + boy);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
