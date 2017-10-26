package com.epam.training.task4.reflection;


import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.InjectCache;

import java.lang.reflect.Field;
import java.util.*;

public class Injector {


    public static <T> void inject(T instance, final Map<String, Cache> classes) throws ReflectionException {
        Class clazz = instance.getClass();

        List<Field> fields = new ArrayList<>();
        fields.addAll(getAllFieldsIncludesSuperclasses(clazz));

        try {
            for (Field field : fields) {
                InjectCache injectCache = field.getDeclaredAnnotation(InjectCache.class);
                if (injectCache == null) {
                    continue;
                }
                Object cache = classes.get(injectCache.name());
                field.setAccessible(true);
                field.set(instance, cache);
            }
        } catch (IllegalAccessException e) {
            throw new ReflectionException("Error creating instance of class or setting field", e);
        }
    }

    private static List<Field> getAllFieldsIncludesSuperclasses(Class instance) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(instance.getDeclaredFields()));
        Class superClass = instance.getSuperclass();
        if (superClass != null) {
            fields.addAll(getAllFieldsIncludesSuperclasses(superClass));
        }
        return fields;
    }
}
