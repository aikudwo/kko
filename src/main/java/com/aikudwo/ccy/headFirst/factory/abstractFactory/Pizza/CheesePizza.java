package com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory.PizzaIngredientFactory;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.Cheese;

/**
 * @author wls
 * @date 2019-07-03 14:09
 */
public class CheesePizza extends Pizza {
    PizzaIngredientFactory ingredientFactory;
    public CheesePizza(PizzaIngredientFactory ingredientFactory){
        this.ingredientFactory = ingredientFactory;
    }

    /**
     * 准备制作pizza的工作交给原料工厂，需要材料就向原料工厂拿
     */
    @Override
   public void prepare() {
        System.out.println("Preparing " + name);
        Cheese cheese = ingredientFactory.createCheese();
        System.out.println();
    }
}
