package com.aikudwo.ccy.utils;

import java.util.*;

/**
 * @author wls
 * @date 2019-05-28 22:29
 */
public class CompareList {

    public static <T> boolean equals(Collection<T> a, Collection<T> b){
        if(a == null){
            return false;
        }
        if(b == null){
            return false;
        }
        if(a.isEmpty() && b.isEmpty()){
            return true;
        }
        if(a.size() != b.size()){
            return false;
        }
        List<T> alist = new ArrayList<T>(a);
        List<T> blist = new ArrayList<T>(b);
        Collections.sort(alist,new Comparator<T>() {
            public int compare(T o1, T o2) {
                return o1.hashCode() - o2.hashCode();
            }

        });

        Collections.sort(blist,new Comparator<T>() {
            public int compare(T o1, T o2) {
                return o1.hashCode() - o2.hashCode();
            }

        });

        return alist.equals(blist);

    }
}
