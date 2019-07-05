package com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.*;

/**
 * @author wls
 * @date 2019-07-04 14:41
 * 定义工厂接口，这个接口负责创建所有pizza的原料
 */
public interface PizzaIngredientFactory {
    Dough createDough();

    Sauce createSauce();

    Cheese createCheese();

    Veggies[] createVeggies();

    Pepperoni createPepperoni();

    Clams createClam();

}
