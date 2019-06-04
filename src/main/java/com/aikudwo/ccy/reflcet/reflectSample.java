package com.aikudwo.ccy.reflcet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wls
 * @date 2019-06-04 19:22
 */
public class reflectSample {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class<?> rc = Class.forName("com.aikudwo.ccy.reflcet.Robot");
        Robot r = (Robot) rc.newInstance();
        Method getHello = rc.getDeclaredMethod("throwHello",String.class);
        getHello.setAccessible(true);
        Object bob = getHello.invoke(r, "bob");
        System.out.println("gethello result is" + bob);

        Method sayHi = rc.getMethod("sayHi", String.class);
        Object name = sayHi.invoke(r, "Welcome");

        Field name1 = rc.getDeclaredField("name");
        name1.setAccessible(true);
        name1.set(r,"Alice");
        sayHi.invoke(r,"go back !!！");

        System.out.println("sayHi result is  "+ name1);


    }
}
