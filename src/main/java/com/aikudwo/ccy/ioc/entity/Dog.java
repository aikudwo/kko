package com.aikudwo.ccy.ioc.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author wls
 * @date 2019-06-03 15:50
 */
@Component
@Primary
public class Dog implements Pet{
    @Override
    public void move() {
        System.out.println("running");
    }
}
