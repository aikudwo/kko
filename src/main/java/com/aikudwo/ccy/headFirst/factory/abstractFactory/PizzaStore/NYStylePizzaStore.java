package com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaStore;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza.CheesePizza;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza.ClamPizza;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.Pizza.Pizza;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory.NYPizzaIngredientFactory;
import com.aikudwo.ccy.headFirst.factory.abstractFactory.PizzaFactory.PizzaIngredientFactory;

/**
 * @author wls
 * @date 2019-07-03 14:20
 */
public class NYStylePizzaStore extends PizzaStore {
    /**
     * 子类自己定义要创建的对象是什么
     * @param type
     * @return
     */
    @Override
    Pizza createPizza(String type) {
        Pizza pizza =null;
        //创建纽约Pizza原料工厂
        PizzaIngredientFactory nyPizzaIngredientFactory = new NYPizzaIngredientFactory();
        if(type.equals("cheese")){
            return new CheesePizza(nyPizzaIngredientFactory);
        }else if(type.equals("Clam")){
            return new ClamPizza(nyPizzaIngredientFactory);
        }else {
            return pizza;
        }
    }
}
