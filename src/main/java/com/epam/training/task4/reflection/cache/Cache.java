package com.epam.training.task4.reflection.cache;

public interface Cache<Integer, String> {
    void put(Integer key, String value);
    String get(Integer key);
}
