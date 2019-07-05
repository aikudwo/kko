package com.aikudwo.ccy.headFirst.factory;

/**
 * @author wls
 * @date 2019-07-03 14:16
 * pizza 商店抽象类
 */
public abstract class PizzaStore{
    public Pizza orderPizza(String type){
        Pizza pizza;
        pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.cut();

        return pizza;
    }

    /**
     * 定义抽象方法，让其子类决定创建的对象是什么，
     * 来达到将对象创建的过程封装的目的
     * @param type
     * @return
     */
    abstract Pizza createPizza(String type);
}
