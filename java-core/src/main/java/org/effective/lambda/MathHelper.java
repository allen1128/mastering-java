package org.effective.lambda;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

public class MathHelper {
    public enum IntegerType {
        Even,
        Odd
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return takeWhile(primes, i -> i <= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }

    //generate prime number with partitioningBy
    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    //generate prime number with custom collector
    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(new PrimeNumberCollector());
    }

    //groupBy
    public static Map<IntegerType, List<Integer>> groupByIntegerType(int n) {
        return IntStream.rangeClosed(0, n).boxed()
                .collect(groupingBy((Integer candidate) -> {
                    if (candidate % 2 == 0) return IntegerType.Even;
                    else return IntegerType.Odd;
                }));
    }

    //groupBy and partitionBy
    public static Map<IntegerType, Map<Boolean, List<Integer>>> groupByIntegerTypeThenPartitionByPrime(int n) {
        return IntStream.rangeClosed(0, n).boxed()
                .collect(groupingBy((Integer candidate) -> {
                    if (candidate % 2 == 0) return IntegerType.Even;
                    else return IntegerType.Odd;
                }, partitioningBy((Integer candidate) -> isPrime(candidate))));
    }

    //random
    public static List<Integer> random(int n, int lowerBound, int upperBound){
        return Stream.generate(Math::random)
                .limit(5)
                .map(i -> ((Double) (i * (upperBound - lowerBound + 1))).intValue() + lowerBound)
                .collect(Collectors.toList());
    }

    private static <A> List<A> takeWhile(List<A> list, Predicate<A> predicate){
        int i = 0;
        for (A a : list){
            if (!predicate.test(a)){
                return list.subList(0, i);
            }
            i++;
        }

        return list;
    }
}