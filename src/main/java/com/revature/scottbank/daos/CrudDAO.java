package com.revature.scottbank.daos;

import com.revature.scottbank.util.collections.List;

public interface CrudDAO<T> {
    T save(T newObj);
    List<T> findAll();
    T findById(String id);
    boolean update(T updatedObj);
    boolean removeById(String id);
}
