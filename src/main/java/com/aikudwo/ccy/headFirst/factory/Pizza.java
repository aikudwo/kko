package com.aikudwo.ccy.headFirst.factory;

import java.util.ArrayList;

/**
 * @author wls
 * @date 2019-07-03 14:04
 * 披萨抽象类
 */
public abstract class Pizza {
    String name;
    String dough;
    String sauce;
    ArrayList toppings = new ArrayList();

    void prepare(){
        System.out.println("Preparing "+ name);
        System.out.println("Tossing dough.....");
        System.out.println("Adding sauce.....");
        System.out.println("Add toppings: ");
        for (Object topping : toppings) {
            System.out.println("   " + topping);
        }
    }

    void bake(){
        System.out.println("Bake for 25 minutes at 350");
    }

    void cut(){
        System.out.println("Cutting the pizza into diagonal slices");
    }

    void box(){
        System.out.println("Place pizza inofficial PizzaStore box");
    }

    public String getName(){
        return name;
    }
}
