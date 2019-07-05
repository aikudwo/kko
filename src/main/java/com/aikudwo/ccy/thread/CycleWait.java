package com.aikudwo.ccy.thread;

/**
 * @author wls
 * @date 2019-05-31 14:54
 * 实现处理线程的返回值
 */
public class CycleWait implements Runnable{
   private String value;

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        value = "成功获得值啦...";
    }

    public static void main(String[] args) throws InterruptedException {
        CycleWait aaa = new CycleWait();
        Thread t = new Thread(aaa);
        t.start();
        /*while (aaa.value == null){
            //主线程等待法
            Thread.currentThread().sleep(100);
        }*/
        /*使用Thread类的join()阻塞当前线程以等到子线程处理完毕
        控制力度不够细
        t.join();*/

        System.out.println(aaa.value);
    }


}
