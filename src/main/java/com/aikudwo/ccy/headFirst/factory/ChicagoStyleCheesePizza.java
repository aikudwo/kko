package com.aikudwo.ccy.headFirst.factory;

/**
 * @author wls
 * @date 2019-07-03 14:13
 */
public class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza(){
        name = "ChicagoSyleCheesePizza";
        dough = "Extr Thick Crust ChicagoDough";
        sauce = "Plum Tomato ChicagoSauce";

        toppings.add("Shredded Mozzarella Cheese");
    }

    void cut(){
        System.out.println("Cutting the pizza into square slices");
    }
}
