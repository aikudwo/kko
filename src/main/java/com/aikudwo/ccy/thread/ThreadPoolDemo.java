package com.aikudwo.ccy.thread;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wls
 * @date 2019-05-31 15:25
 * 使用线程池 获取线程返回值
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> submit = executorService.submit(new MyCallable());
        if(!submit.isDone()){
            System.out.println("task is doing");
        }
        try {
            String s = submit.get();
            System.out.println("task is doing  :" + s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
