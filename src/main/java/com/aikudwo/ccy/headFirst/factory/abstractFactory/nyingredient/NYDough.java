package com.aikudwo.ccy.headFirst.factory.abstractFactory.nyingredient;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.Dough;

/**
 * @author wls
 * @date 2019-07-04 15:25
 */
public class NYDough extends Dough {
    private String doughName = "来自纽约的面团 ！！！";
    public NYDough() {

    }

    public String getDoughName() {
        return doughName;
    }
}
