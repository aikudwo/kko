package com.aikudwo.ccy.headFirst.Strategy;

/**
 * @author wls
 * @date 2019-07-02 14:24
 */
public class MallardDuck extends Duck {
    //各种鸭子自己实现飞和叫的行为，(由于飞和叫的行为是可变所以抽离出去)
    public MallardDuck(){
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }

    @Override
    public void display() {
        System.out.println("外观是绿色的鸭子！！！");

    }
}
