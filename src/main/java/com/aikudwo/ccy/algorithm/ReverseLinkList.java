package com.aikudwo.ccy.algorithm;

import java.util.LinkedList;

/**
 * @author wls
 * @date 2019-06-06 16:07
 * 反转链表
 */
public class ReverseLinkList {
    public static void main(String[] args) {
        LinkedList<Integer> integers = new LinkedList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);
        integers.add(7);
        integers.add(17);
        integers.add(27);
        integers.add(39);

        int lengh = 0;
        int temp = 0;
        int size = integers.size();
        if (size % 2 != 0) {
            lengh = (size + 1) / 2;
        } else {
            lengh = size / 2;
        }
        for (int i = 0; i < lengh - 1; i++) {
            temp = integers.get(i);
            integers.set(i, integers.get(integers.size() - (i + 1)));
            integers.set(integers.size() - (i + 1), temp);
        }
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }
}
