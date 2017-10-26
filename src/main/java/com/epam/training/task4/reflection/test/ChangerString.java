package com.epam.training.task4.reflection.test;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.InjectCache;

public class ChangerString {

    @InjectCache(name = "REVERSE")
    private Cache<Integer, String> cache;

    public String get(Integer key) {
        return cache.get(key);
    }

    public void put(Integer key, String value) {
        cache.put(key, value);
    }

}
