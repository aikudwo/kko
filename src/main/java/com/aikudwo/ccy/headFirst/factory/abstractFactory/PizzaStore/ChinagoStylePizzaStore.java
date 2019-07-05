package com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaStore;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza.ClamPizza;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza.Pizza;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza.SaucePizza;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory.ChicagoPizzaIngredientFactory;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory.PizzaIngredientFactory;

/**
 * @author wls
 * @date 2019-07-03 14:24
 */
public class ChinagoStylePizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory chicagoPizzaIngredientFactory = new ChicagoPizzaIngredientFactory();
        if(type.equals("cheese")){
            return new ClamPizza(chicagoPizzaIngredientFactory);
        }else if(type.equals("Sauce")){
            return new SaucePizza(chicagoPizzaIngredientFactory);
        }else if(type.equals("clam")){
            return new ClamPizza(chicagoPizzaIngredientFactory);
        }else {
            return pizza;
        }
    }
}
