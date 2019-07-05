package com.aikudwo.ccy.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wls
 * @date 2019-06-26 10:28
 */
public class StreamAction {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        //1. Find all transactions in the year 2011 and sort them by value (small to high).
        transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        //2.What are all the unique cities where the traders work?
        transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);

        //3.Find all traders from Cambridge and sort them by name.
        transactions.stream().map(transaction -> transaction.getTrader())
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName));

        //4.Return a string of all traders’ names sorted alphabetically
        transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (t1, t2) -> t1 + t2);

        //5. Are any traders based in Milan?
        transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

        //6.Print all transactions’ values from the traders living in Cambridge.
        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .forEach(System.out::print);

        //7.What’s the highest value of all the transactions?
        transactions.stream().map(Transaction::getValue)
                .reduce(Integer::max);
            //    .reduce((i, j) -> i > j ? i : j);

    }


}
