package com.epam.training.task4.reflection.cache.implementation;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.CacheDeclaration;


@CacheDeclaration(name = "LOWER_CASE")
public class CacheLowerCaseValue extends Base implements Cache<Integer, String> {

    public CacheLowerCaseValue(){
        super();
    }

    @Override
    public void put(Integer key, String value) {
        super.put(key, value.toLowerCase());
    }

    @Override
    public String get(Integer key) {
        return super.get(key);
    }
}
