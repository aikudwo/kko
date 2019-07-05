package com.aikudwo.ccy.headFirst.factory;

/**
 * @author wls
 * @date 2019-07-03 14:20
 */
public class NYStylePizzaStore extends PizzaStore{
    /**
     * 子类自己定义要创建的对象是什么
     * @param type
     * @return
     */
    @Override
    Pizza createPizza(String type) {
        if(type.equals("cheese")){
            return new NYStyleCheesePizza();
        }else if(type.equals("pepperoni")){
            return new NYStyleCheesePizza();
        }else if(type.equals("clam")){
            return new NYStyleCheesePizza();
        }else if(type.equals("veggie")){
            return new NYStyleCheesePizza();
        }else {
            return null;
        }
    }
}
