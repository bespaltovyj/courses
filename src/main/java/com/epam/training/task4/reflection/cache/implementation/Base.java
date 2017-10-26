package com.epam.training.task4.reflection.cache.implementation;

import com.epam.training.task4.reflection.cache.Cache;

import java.util.HashMap;
import java.util.Map;

public abstract class Base implements Cache<Integer, String> {

    private Map<Integer, String> cache = new HashMap<>();

    @Override
    public void put(Integer key, String value) {
        cache.put(key, value);
    }

    @Override
    public String get(Integer key) {
        return cache.get(key);
    }


}
