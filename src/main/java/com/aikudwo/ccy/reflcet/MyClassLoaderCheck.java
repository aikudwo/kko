package com.aikudwo.ccy.reflcet;

/**
 * @author wls
 * @date 2019-06-04 20:23
 */
public class MyClassLoaderCheck {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader m = new MyClassLoader("F:/", "myClassLoader");
        Class<?> wali = m.loadClass("Wali");
        System.out.println(wali.getClassLoader());
        System.out.println(wali.getClassLoader().getParent());
        System.out.println(wali.getClassLoader().getParent().getParent());
        System.out.println(wali.getClassLoader().getParent().getParent().getParent());
        wali.newInstance();
    }
}
