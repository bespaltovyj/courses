package com.epam.training.task4.reflection.cache.implementation;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.CacheDeclaration;


@CacheDeclaration(name = "UPPER_CASE")
public class CacheUpperCaseValue extends Base implements Cache<Integer, String> {

    public CacheUpperCaseValue() {
        super();
    }

    @Override
    public void put(Integer key, String value) {
        super.put(key, value.toUpperCase());
    }

    @Override
    public String get(Integer key) {
        return super.get(key);
    }
}
