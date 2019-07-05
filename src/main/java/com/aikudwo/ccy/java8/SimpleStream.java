package com.aikudwo.ccy.java8;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @author wls
 * @date 2019-06-25 19:05
 */
public class SimpleStream {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        List<String> dishNamesByCollecttions = getDishNamesByCollecttions(menu);
        //System.out.println(dishNamesByCollecttions);
        List<String> dishNamesByStream = getDishNamesByStream(menu);
        System.out.println(dishNamesByStream);
    }

    private static List<String> getDishNamesByStream(List<Dish> menu) {
        List<String> collect = menu.parallelStream().filter(dish -> {
            /*try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            return dish.getCalories()<400;
        }).sorted(Comparator.comparingInt(Dish::getCalories))
                .map(Dish::getName)
                .limit(1)
                .distinct()
                .collect(toList());
        return collect;
    }

    private static List<String> getDishNamesByCollecttions(List<Dish> menu) {
        ArrayList<Dish> lowCalories = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCalories.add(dish);
            }
        }
        Collections.sort(lowCalories, (s1, s2) -> Integer.compare(s1.getCalories(), s2.getCalories()));

        ArrayList<String> strings = new ArrayList<>();

        for (Dish lowCalory : lowCalories) {
            strings.add(lowCalory.getName());

        }
        return strings;

    }
}
