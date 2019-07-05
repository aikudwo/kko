package com.aikudwo.ccy.headFirst.factory;

/**
 * @author wls
 * @date 2019-07-03 14:24
 */
public class ChinagoStylePizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String type) {
        if(type.equals("cheese")){
            return new ChicagoStyleCheesePizza();
        }else if(type.equals("pepperoni")){
            return new ChicagoStyleCheesePizza();
        }else if(type.equals("clam")){
            return new ChicagoStyleCheesePizza();
        }else if(type.equals("veggie")){
            return new ChicagoStyleCheesePizza();
        }else {
            return null;
        }
    }
}
