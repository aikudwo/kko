package com.aikudwo.ccy.thread;

/**
 * @author wls
 * @date 2019-05-31 14:38
 */
public class RunnableDemo {
    public static void main(String[] args) {
        MyRunnable a = new MyRunnable("aaa");
        MyRunnable b = new MyRunnable("b");
        MyRunnable c = new MyRunnable("c");
        new Thread(c).start();
        new Thread(b).start();
        new Thread(a).start();
    }
}
