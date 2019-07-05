package com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.chicagoingredient.*;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.*;

/**
 * @author wls
 * @date 2019-07-04 15:49
 */
public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ChicagoDough();
    }

    @Override
    public Sauce createSauce() {
        return new ChicagoSauce();
    }

    @Override
    public Cheese createCheese() {
        return new ChicagoCheese();
    }

    @Override
    public Veggies[] createVeggies() {
        return new Veggies[0];
    }

    @Override
    public Pepperoni createPepperoni() {
        return new ChicagoPepperoni();
    }

    @Override
    public Clams createClam() {
        return new ChicagoClams();
    }
}
