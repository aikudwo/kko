package com.aikudwo.ccy.aop;

/**
 * @author wls
 * @date 2019-06-03 17:42
 */
public class PayProxyDemo {
    public static void main(String[] args) {
        Pay alipay = new Alipay(new Realpay());
        alipay.pay();
    }
}
