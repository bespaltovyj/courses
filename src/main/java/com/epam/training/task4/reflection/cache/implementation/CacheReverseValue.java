package com.epam.training.task4.reflection.cache.implementation;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.CacheDeclaration;


@CacheDeclaration(name = "REVERSE")
public class CacheReverseValue extends Base implements Cache<Integer, String> {

    public CacheReverseValue() {
        super();
    }

    @Override
    public void put(Integer key, String value) {
        value = new StringBuilder(value).reverse().toString();
        super.put(key, value);
    }

    @Override
    public String get(Integer key) {
        return super.get(key);
    }
}
