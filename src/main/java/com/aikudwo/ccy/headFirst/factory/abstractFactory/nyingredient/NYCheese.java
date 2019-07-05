package com.aikudwo.ccy.headFirst.factory.abstractFactory.nyingredient;

import com.aikudwo.ccy.headFirst.factory.abstractFactory.ingredient.Cheese;

/**
 * @author wls
 * @date 2019-07-04 15:28
 */
public class NYCheese extends Cheese {
    public NYCheese() {
    }

    private String cheeseName ="来自纽约的起司！！！";

    public String getCheeseName() {
        return cheeseName;
    }
}
