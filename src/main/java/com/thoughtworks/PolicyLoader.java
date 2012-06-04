package com.thoughtworks;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class PolicyLoader {

    public Policy loadDefaultPolicy() throws FileNotFoundException {
        InputStream input = new FileInputStream(new File("src/test/resources/data/default-policy.yaml"));

        Constructor constructor = new Constructor(Policy.class);
        TypeDescription policyDescription = new TypeDescription(Policy.class);
        policyDescription.putListPropertyType("drivers", Driver.class);
        constructor.addTypeDescription(policyDescription);
        Yaml yaml = new Yaml(constructor);

        return yaml.loadAs(input, Policy.class);
    }

    public Object loadDefaultPolicyAsMap() throws FileNotFoundException {
        InputStream input = new FileInputStream(new File("src/test/resources/data/default-policy.yaml"));
        Yaml yaml = new Yaml();
        return yaml.load(input);
    }

    public Object loadPartialPolicy(String path) throws FileNotFoundException {
        InputStream input = new FileInputStream(new File(path));
        Yaml yaml = new Yaml(new Constructor());

        return yaml.load(input);
    }

    public Policy loadPolicy(String path) throws FileNotFoundException, IntrospectionException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Policy policy = loadDefaultPolicy();
        Map<String, Object> partialPolicyMap = (Map) loadPartialPolicy(path);

        updateObject(policy, partialPolicyMap);
        return policy;
    }

    private void updateObject(Object object, Map<String, Object> partialObjectMap) {
        try {
            for (Map.Entry<String, Object> entry : partialObjectMap.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Map) {
                    updateObject(getFieldObject(object, fieldName), (Map) value);
                } else if (value instanceof List) {
                    processList(object, fieldName, (List) value);
                } else {
                    object = ifListGetObject(object);
                    updateField(object, fieldName, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object ifListGetObject(Object object) {
        if (object instanceof List) {
            object = ((List) object).get(0);
        }
        return object;
    }

    private void processList(Object object, String fieldName, List values) {
        for (Object objectDef : values) {
            Map objectDefMap = (Map) objectDef;
            Iterator entriesIterator = objectDefMap.entrySet().iterator();
            String command = (String) entriesIterator.next();
            if (command.equals("update")) {
                System.out.println("updating...");
            } else if (command.equals("add")) {
                System.out.println("adding...");
            }
        }
    }

    private void updateObjects(Object object, String fieldName, List value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Object item : (List) value) {
            updateObject(getFieldObject(object, fieldName), (Map) item);
        }
    }

    private void updateField(Object object, String fieldName, Object value) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field field = object.getClass().getDeclaredField(fieldName);
        Method method = object.getClass().getMethod("set" + capitalize(fieldName), field.getType());
        method.invoke(object, value);
    }

    private Object getFieldObject(Object object, String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getter = "get" + capitalize(fieldName);
        Method method = object.getClass().getMethod(getter);
        return method.invoke(object);
    }

}
