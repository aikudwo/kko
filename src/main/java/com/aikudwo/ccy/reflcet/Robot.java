package com.aikudwo.ccy.reflcet;

/**
 * @author wls
 * @date 2019-06-04 19:20
 */
public class Robot {
    private String name;
    public void sayHi(String helloSentence){
        System.out.println(helloSentence+ " " + name);
    }
    private String throwHello(String tag){
        return "hello" + " " + tag;
    }
}
