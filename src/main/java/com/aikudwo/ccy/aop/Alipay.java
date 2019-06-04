package com.aikudwo.ccy.aop;

/**
 * @author wls
 * @date 2019-06-03 17:39
 */
public class Alipay implements Pay {
    private Realpay realpay;
    public Alipay(Realpay realpay){
        this.realpay = realpay;
    }
    public void befor(){
        System.out.println("从银行卡出钱");
    }
    @Override
    public void pay() {
        befor();
        realpay.pay();
        after ();
    }

    public void after (){

        System.out.println("记录支付记录");
    }
}
