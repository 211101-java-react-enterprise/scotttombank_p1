package com.revature.scottbank.util.collections;

// Custom ADT (abstract data type)
public interface Collection<T> {

    boolean add(T element);
    boolean contains(T element);
    boolean isEmpty();
    boolean remove(T element);
    int size();

}
