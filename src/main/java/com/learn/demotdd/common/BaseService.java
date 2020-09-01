package com.learn.demotdd.common;

public interface BaseService<T> {

    void save(T vo);

    T getOne(String id);
}
