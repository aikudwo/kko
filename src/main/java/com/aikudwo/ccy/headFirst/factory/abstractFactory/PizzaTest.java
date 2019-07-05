package com.aikudwo.ccy.headFirst.factory.abstractFactory;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza.Pizza;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaStore.ChinagoStylePizzaStore;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaStore.NYStylePizzaStore;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaStore.PizzaStore;

/**
 * @author wls
 * @date 2019-07-03 14:26
 */
public class PizzaTest {
    public static void main(String[] args) {
        PizzaStore nyStylePizzaStore = new NYStylePizzaStore();
        PizzaStore chinagoStylePizzaStore = new ChinagoStylePizzaStore();

        Pizza pizza = nyStylePizzaStore.orderPizza("cheese");
        System.out.println("NY Pizza  " +pizza.getName());

        Pizza pizza1 = chinagoStylePizzaStore.orderPizza("cheese");
        System.out.println("China Pizaa  " + pizza1.getName());
    }
}
