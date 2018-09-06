package org.effective.generics;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class CustomStack <E extends Number> {
    protected List<E> elements;

    public CustomStack(){
        elements = new ArrayList<>();
    }

    public void push(E e){
        elements.add(e);
    }

    public E pop(){
        if (elements.size() < 1) {
            throw new EmptyStackException();
        }
        E e = elements.get(0);
        elements = elements.subList(1, elements.size());
        return e;
    }

    public List<? super E> popSome(int size){
        List<? super E> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(this.pop());
        }
        return result;
    }

    public void pushSome(List<? extends E> newList){
        elements.addAll((List<E>) newList);
    }

    public int size(){
        return elements.size();
    }

    public void reverse(){
        int start = 0;
        int end = elements.size() - 1;

        while (start < end) {
            E v = elements.get(start);
            elements.set(start, elements.get(end));
            elements.set(end, v);
            start++;
            end--;
        }
    }

    @Override
    public String toString() {
        return "CustomStack{" +
                "elements=" + elements +
                '}';
    }
}
