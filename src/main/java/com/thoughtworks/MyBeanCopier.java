package com.thoughtworks;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class MyBeanCopier {

    public void createBeanFromMap(Map map) {
        String className = (String) map.get(0);

    }

    public void copy(Object destination, Object source) throws IntrospectionException {
        BeanInfo sourceInfo = Introspector.getBeanInfo(source.getClass());
        BeanInfo destinationInfo = Introspector.getBeanInfo(destination.getClass());

        PropertyDescriptor[] sourceDescriptors = sourceInfo.getPropertyDescriptors();
        PropertyDescriptor[] destinationDescriptors = destinationInfo.getPropertyDescriptors();

        // for each property in partial
        for (PropertyDescriptor sourceDescriptor : sourceDescriptors) {
            // find it's respective place in the blueprint
            for (PropertyDescriptor destinationDescriptor : destinationDescriptors) {
                if (sourceDescriptor.getName().equals(destinationDescriptor.getName())) {
                    // and copy it into the blueprint
//                    destinationDescriptor.setValue(sourceDescriptor.getValue());
                }
            }
        }
    }
}
