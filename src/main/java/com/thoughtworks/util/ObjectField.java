package com.thoughtworks.util;

import java.lang.reflect.Field;

public class ObjectField {
    private Object object;
    private String fieldName;

    public ObjectField(Object object, String fieldName) {
        this.object = object;
        this.fieldName = fieldName;
    }

    public static ObjectField field(Object object, String fieldName) {
        return new ObjectField(object, fieldName);
    }

    public Object getInstance() {
        try {
            Field declaredField = getAccessibleField();
            Object field = declaredField.get(object);
            if (field == null) {
                field = declaredField.getType().newInstance();
                setObjectField(field);
            }
            return field;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setObjectField(Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getAccessibleField();
        field.set(object, value);
    }

    private Object getObjectField() throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = getAccessibleField();
        return declaredField.get(object);
    }

    private Field getAccessibleField() throws NoSuchFieldException {
        Field declaredField = object.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        return declaredField;
    }
}
