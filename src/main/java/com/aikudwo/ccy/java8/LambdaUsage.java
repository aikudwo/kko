package com.aikudwo.ccy.java8;

/**
 * @author wls
 * @date 2019-06-10 16:53
 */
public class LambdaUsage {
    public static void main(String[] args) {
        Runnable r = ()-> System.out.println("aaaaaa");
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("bbbbbbbbbbb");
            }
        };
        process(r);
        process(r1);
        process(()-> System.out.println("ccccccccccc"));
    }
    private static void process(Runnable runnable){
        runnable.run();
    }
}
