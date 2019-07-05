package com.aikudwo.ccy.algorithm;

import java.util.ArrayList;

/**
 * @author wls
 * @date 2019-06-06 09:51
 */
public class FibonacciSequence {
    private Integer Fibonacci1(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return new FibonacciSequence().Fibonacci1(n - 1) + new FibonacciSequence().Fibonacci1(n - 2);
    }

    private Integer Fibonacci2(int n) {
        int sum1 = 1;
        int sum2 = 1;
        int tem = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (n == 1 || n == 2) {
                return sum1;
            }
            if (i > 2) {
                tem = sum2;
                sum2 = sum1 + sum2;
                sum1 = tem;
                sum = sum1 + sum2;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int n = 7;
        Integer feibolaqi = new FibonacciSequence().Fibonacci1(n);
        System.out.println(feibolaqi);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        boolean contains = integers.contains(1);
        if(contains){
            System.out.println("6666666666666666");
        }
    }
}
