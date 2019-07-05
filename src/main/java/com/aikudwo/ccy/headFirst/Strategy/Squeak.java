package com.aikudwo.ccy.headFirst.Strategy;

/**
 * @author wls
 * @date 2019-07-02 14:36
 */
public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("橡皮鸭吱吱叫！！！");
    }
}
