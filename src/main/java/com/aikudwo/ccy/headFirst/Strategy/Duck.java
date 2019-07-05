package com.aikudwo.ccy.headFirst.Strategy;

/**
 * @author wls
 * @date 2019-07-02 14:19
 */
public abstract class Duck {
    QuackBehavior quackBehavior;
    FlyBehavior flyBehavior;

    public void performFly(){
        flyBehavior.fly();  //委托给行为类
    }

    public void performQuack(){
        quackBehavior.quack();  //委托给行为类
    }

    public void swim(){
        System.out.println("来自鸭子的特性：游泳！！！");
    }

    //由于每个鸭子的行为不同，所以行为方法定义成抽象的，由各个鸭子自己实现
    public abstract void display();
}
