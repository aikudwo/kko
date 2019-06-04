package com.aikudwo.ccy.ioc.entity;

import org.springframework.stereotype.Component;

/**
 * @author wls
 * @date 2019-06-03 15:54
 */
@Component
public class Bird implements Pet{
    @Override
    public void move() {
        System.out.println("flying");
    }
}
