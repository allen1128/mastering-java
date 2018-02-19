package com.org.effective.com.org.effective.generics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Client {
    public static void main(String[] args) {
        CustomStack<Number> customStack = new CustomStack<>();
        List<Number> list = new ArrayList<>();
        list.add(new Integer(1));
        list.add(new Double(0.1d));
        list.add(new Double(4.0f));
        customStack.pushSome(list);
        System.out.println(customStack);
        customStack.reverse();
        System.out.println(customStack);
        System.out.println(customStack.pop());
        System.out.println(customStack.popSome(2));

        AdvancedCustomStack advancedCustomStack = new AdvancedCustomStack();
        advancedCustomStack.pushSome(new ArrayList<Integer>(Arrays.asList(1, 2, 6, 3, 4, 5, 7)));
        advancedCustomStack.sort();
        System.out.println(advancedCustomStack);

        Set<Integer> set1 = new HashSet<>();
        set1.add(5);
        set1.add(2);
        set1.add(1);

        System.out.println(set1);

        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(3);
        set2.add(30);

        Set<Integer> set3 = CollectionHelper.union(set1, set2);
        System.out.println(set3);

        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 5, 3, 2));
        System.out.println(list2);
        CollectionHelper.sort(list2);
        System.out.println(list2);
    }
}

