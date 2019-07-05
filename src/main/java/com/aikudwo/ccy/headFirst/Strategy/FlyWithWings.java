package com.aikudwo.ccy.headFirst.Strategy;

/**
 * @author wls
 * @date 2019-07-02 14:32
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("飞起来啦！！！");
    }
}
