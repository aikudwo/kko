package com.aikudwo.ccy.aop;

/**
 * @author wls
 * @date 2019-06-03 17:38
 */
public class Realpay implements Pay {
    @Override
    public void pay(){
        System.out.println("个人支付金额");
    }
}
