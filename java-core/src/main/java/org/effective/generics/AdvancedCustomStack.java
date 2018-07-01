package org.effective.generics;

import java.util.Comparator;

public final class AdvancedCustomStack extends CustomStack<Integer> {
    public void sort(){
        this.elements.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
    }
}
