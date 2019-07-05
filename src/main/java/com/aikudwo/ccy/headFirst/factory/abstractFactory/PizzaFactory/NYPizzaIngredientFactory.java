package com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.*;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.nyingredient.*;

/**
 * @author wls
 * @date 2019-07-04 14:47
 * 创建纽约原料工厂  返回特有的原料
 */
public class NYPizzaIngredientFactory implements PizzaIngredientFactory{
    @Override
    public Dough createDough() {
        return new NYDough();
    }

    @Override
    public Sauce createSauce() {
        return new NYSauce();
    }

    @Override
    public Cheese createCheese() {
        return new NYCheese();
    }

    @Override
    public Veggies[] createVeggies() {
        return new NYVeggies[0];
    }

    @Override
    public Pepperoni createPepperoni() {
        return new NYPepperoni();
    }

    @Override
    public Clams createClam() {
        return new NYClams();
    }
}
