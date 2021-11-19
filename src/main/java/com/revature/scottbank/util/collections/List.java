package com.revature.scottbank.util.collections;

import com.revature.scottbank.util.collections.Collection;

// Custom ADT inheriting from custom ADT
public interface List<T> extends Collection<T> {

    T get(int index);

}
