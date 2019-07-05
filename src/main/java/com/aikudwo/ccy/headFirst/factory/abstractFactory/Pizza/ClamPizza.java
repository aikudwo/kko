package com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory.PizzaIngredientFactory;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.Clams;

/**
 * @author wls
 * @date 2019-07-03 14:13
 */
public class ClamPizza extends Pizza {
    PizzaIngredientFactory ingredientFactory;
    public ClamPizza(PizzaIngredientFactory ingredientFactory){
        this.ingredientFactory=ingredientFactory;
    }
    @Override
   public void prepare() {
        System.out.println("Preparing " + name);
        Clams clam = ingredientFactory.createClam();
    }
}
