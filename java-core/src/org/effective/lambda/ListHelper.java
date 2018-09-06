package org.effective.lambda;

import java.util.ArrayList;
import java.util.List;

public class ListHelper {
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate){
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)){
                result.add(t);
            }
        }

        return result;
    }
}
