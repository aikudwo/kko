package com.aikudwo.ccy.java8;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author wls
 * @date 2019-06-25 20:41
 */
public class StreamMatch {
    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 6, 7, 7, 1});
        //boolean b = stream.allMatch(integer -> integer > 10);

        boolean b1 = stream.anyMatch(integer -> integer > 6);
        System.out.println(b1);
    }
}
