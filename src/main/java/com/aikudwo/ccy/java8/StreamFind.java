package com.aikudwo.ccy.java8;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author wls
 * @date 2019-06-25 20:45
 */
public class StreamFind {
    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 3, 4, 5, 6, 6, 7, 7, 1});
        Optional<Integer> any = stream.filter(integer -> integer % 2 == 0).findAny();
        System.out.println(any.get());
    }
}
