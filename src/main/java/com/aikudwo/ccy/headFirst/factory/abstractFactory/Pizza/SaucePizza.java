package com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory.PizzaIngredientFactory;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.Cheese;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.Sauce;

/**
 * @author wls
 * @date 2019-07-04 16:13
 */
public class SaucePizza extends Pizza{
    PizzaIngredientFactory ingredientFactory;
    public SaucePizza(PizzaIngredientFactory ingredientFactory){
        this.ingredientFactory=ingredientFactory;
    }
    @Override
   public void prepare() {
        System.out.println("Preparing " + name);
        Sauce sauce = ingredientFactory.createSauce();
        Cheese cheese = ingredientFactory.createCheese();
    }
}
