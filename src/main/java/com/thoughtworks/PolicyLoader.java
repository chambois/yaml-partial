package com.thoughtworks;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static com.thoughtworks.util.TargetObject.targetObject;

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

    public Policy loadPolicy(String path) throws FileNotFoundException, IntrospectionException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Policy policy = loadDefaultPolicy();
        Map<String, Object> partialPolicyMap = (Map) loadPartialPolicy(path);

        targetObject(policy).update(partialPolicyMap);
        return policy;
    }

    private Object loadPartialPolicy(String path) throws FileNotFoundException {
        InputStream input = new FileInputStream(new File(path));
        Yaml yaml = new Yaml(new Constructor());

        return yaml.load(input);
    }
}
