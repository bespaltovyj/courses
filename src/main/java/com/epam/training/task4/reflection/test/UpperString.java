package com.epam.training.task4.reflection.test;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.InjectCache;

public class UpperString {

    @InjectCache(name = "UPPER_CASE")
    private Cache<Integer, String> cache;

    public String get(Integer key) {
        return cache.get(key);
    }

    public void put(Integer key, String value) {
        cache.put(key, value);
    }
}
