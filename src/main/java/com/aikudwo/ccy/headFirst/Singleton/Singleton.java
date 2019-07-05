package com.aikudwo.ccy.headFirst.Singleton;

/**
 * @author wls
 * @date 2019-07-05 11:19
 */
public class Singleton {
    private static Singleton singleton;
    private static Singleton singletons = new Singleton();
    private Singleton() {
    }
    //创建简单的单列 （懒加载）
    public static Singleton getInstanceLazy(){
        if(singleton == null){
            singleton= new Singleton();
        }
        return singleton;
    }

    //创建简单的单列 （急迫的加载）
    //利用这种方式，我们可以依赖JVM在加载这个类的时候马上创建唯一此单件实列，保证线程安全
    public static Singleton getInstanceEagrly(){
        return singletons;
    }

    //创建同步(锁)的单列
    public synchronized static Singleton getInstanceSynchronized(){
        if(singleton == null){
            singleton= new Singleton();
        }
        return singleton;
    }

    //用双重检查加锁  在方法中减少使用同步
    public static Singleton getInstanceTwoCheck(){
        if(singleton == null){
            synchronized (Singleton.class){
                if(singleton==null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
