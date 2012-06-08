package com.thoughtworks.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class Lists {
    public static Class getGenericTypeClass(Object object, String fieldName) {
        try {
            Field listField = object.getClass().getDeclaredField(fieldName);
            ParameterizedType stringListType = (ParameterizedType) listField.getGenericType();
            Class<?> listClass = (Class<?>) stringListType.getActualTypeArguments()[0];
            return listClass;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
