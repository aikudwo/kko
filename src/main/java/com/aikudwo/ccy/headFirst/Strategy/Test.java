package com.aikudwo.ccy.headFirst.Strategy;

/**
 * @author wls
 * @date 2019-07-02 14:46
 */
public class Test {

    public static void main(String[] args) {
        Duck mallardDuck = new MallardDuck();
        mallardDuck.performFly();
        mallardDuck.performQuack();
        mallardDuck.display();

    }
}
