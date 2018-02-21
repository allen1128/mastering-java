package com.org.effective.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;

public class Client {
    public static void main(String[] args) {
        //use lambda expression
        List<String> list = Arrays.asList("orange", "ORANGE", "apple", "pear");
        List<String> oranges = ListHelper.filter(list, (String str) -> str.equalsIgnoreCase("orange"));
        System.out.println(oranges);


        //join strings
        String orangeNames = oranges.stream().collect(joining());
        System.out.printf("Joinged orange names in different cases： %s %n", orangeNames);

        //use java stream api
        List<Integer> numbers = Arrays.asList(1, 20, 12, -5, 3, -2, 0);
        int max = numbers.stream().reduce(Integer.MIN_VALUE, Math::max);
        System.out.printf("Max %d of %s %n", max, numbers.toString());

        //returned Optional type object
        Optional<Integer> oMax = numbers.stream().reduce(Math::max);
        System.out.printf("Max %d of %s %n", oMax.orElse(Integer.MIN_VALUE), numbers.toString());

        //method inference
        List<Double> prices = Arrays.asList(1.1d, 2.4d, 3.01d);
        List<Dish> dishes = prices.stream().map(Dish::new).collect(Collectors.toList());
        System.out.println(dishes);

        //use primitive tpe streasm api: DoubleStream
        double sum = dishes.stream().mapToDouble(Dish::getPrice).sum();
        System.out.printf("sum of all dish price: %f %n", sum);

        //split to create a stream for each word and flatmap to flatten the streams to one single stream.
        List<String> words = Arrays.asList("Hello", "World");
        List<String> distinctCharacter = words.stream()
                .map(str -> str.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(distinctCharacter);

        //generate unlimited stream
        Stream.iterate(0, n -> n + 2)
                .limit(20)
                .forEach(System.out::println);

        //generate Fibonacci Sequence array 1
        List<int[]> arrays = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .collect(Collectors.toList());

        arrays.stream().forEach(t -> System.out.printf("(%d,%d)%n", t[0], t[1]));

        //generate Fibonacci Sequence array 2
        List<Integer> arrays2 = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t->t[0])
                .collect(Collectors.toList());

        System.out.print("(");
        arrays2.stream().forEach(t -> System.out.printf("%d ", t));
        System.out.println(")");

        //generate primes and partition
        System.out.println(MathHelper.partitionPrimes(100));

        //generate primes with custom collector
        System.out.println(MathHelper.partitionPrimesWithCustomCollector(100));

        //group by
        System.out.println(MathHelper.groupByIntegerType(100));

        //group by and partition by
        System.out.println(MathHelper.groupByIntegerTypeThenPartitionByPrime(100));

        //random generation
        System.out.println(MathHelper.random(5, 5, 6));

    }
}
