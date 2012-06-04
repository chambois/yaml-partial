package com.thoughtworks;

import org.apache.commons.beanutils.BeanUtilsBean;
import java.lang.reflect.InvocationTargetException;

// taken from:
// http://stackoverflow.com/questions/1301697/helper-in-order-to-copy-non-null-properties-from-object-to-another-java
public class NullAwareBeanUtilsBean extends BeanUtilsBean {

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value == null) return;
//        if (value.getClass().getDeclaredFields().length != 0) {
//
//        }
        
        super.copyProperty(dest, name, value);
    }

}