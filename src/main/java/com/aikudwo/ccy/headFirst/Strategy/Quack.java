package com.aikudwo.ccy.headFirst.Strategy;

/**
 * @author wls
 * @date 2019-07-02 14:35
 */
public class Quack implements QuackBehavior
{
    @Override
    public void quack() {
        System.out.println("鸭子正常的叫！！！");
    }
}
