package com.aikudwo.ccy.thread;

/**
 * @author wls
 * @date 2019-05-31 14:31
 */
public class ThreadDome {
    public static void main(String[] args) {
        MyThread aaa = new MyThread("aaa");
        MyThread bbb = new MyThread("bbb");
        MyThread ccc = new MyThread("ccc");
        aaa.start();
        bbb.start();
        ccc.start();
    }
}
