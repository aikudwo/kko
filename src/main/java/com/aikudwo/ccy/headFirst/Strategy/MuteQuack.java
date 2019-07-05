package com.aikudwo.ccy.headFirst.Strategy;

/**
 * @author wls
 * @date 2019-07-02 14:37
 */
public class MuteQuack implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("一只不会叫的鸭子！！！");
    }
}
