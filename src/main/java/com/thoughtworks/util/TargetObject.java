package com.thoughtworks.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.util.ObjectField.field;
import static com.thoughtworks.util.Lists.getGenericTypeClass;
import static org.apache.commons.lang3.StringUtils.capitalize;

public class TargetObject {
    private Object targetObject;

    public TargetObject(Object object) {
        this.targetObject = object;
    }

    public static TargetObject targetObject(Object object) {
        return new TargetObject(object);
    }

    public void update(Map<String, Object> values) {
        try {
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Map) {
                    new TargetObject(getFieldObject(targetObject, fieldName)).update((Map) value);
                } else if (value instanceof List) {
                    processList(targetObject, fieldName, (List) value);
                } else {
                    targetObject = ifListGetObject(targetObject);
                    updateField(targetObject, fieldName, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateField(Object object, String fieldName, Object value) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field field = object.getClass().getDeclaredField(fieldName);
        Method method = object.getClass().getMethod("set" + capitalize(fieldName), field.getType());
        method.invoke(object, value);
    }

    private Object getFieldObject(Object object, String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return field(object, fieldName).getInstance();
    }

    private void processList(Object object, String fieldName, List values) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        // Get the collection
        List listObject = (List) getFieldObject(object, fieldName);
        Class listElementsClass = getGenericTypeClass(object, fieldName);
        for (Object value : values) {
            Object listElement = listElementsClass.newInstance();
            new TargetObject(listElement).update((Map) value);
            listObject.add(listElement);
        }
    }

    private Object ifListGetObject(Object object) {
        if (object instanceof List) {
            object = ((List) object).get(0);
        }
        return object;
    }
}
