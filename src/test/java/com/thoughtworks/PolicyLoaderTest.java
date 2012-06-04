package com.thoughtworks;


import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PolicyLoaderTest {

    private PolicyLoader policyLoader = new PolicyLoader();
    private Policy defaultPolicy;

    @Before
    public void setUp() throws Exception {
        defaultPolicy = policyLoader.loadDefaultPolicy();
    }

    @Test
    public void shouldLoadDefaultPolicyFromYamlFile() throws Exception {

        assertNotNull(defaultPolicy);
        assertThat(defaultPolicy.getDrivers().get(0).getFirstName(), is("Joe"));
        assertThat(defaultPolicy.getDrivers().get(0).getLastName(), is("Smith"));
        assertThat(defaultPolicy.getDrivers().get(0).getAge(), is(44));
        assertThat(defaultPolicy.getCar().getMake(), is("Ford"));
    }

    @Test
    public void shouldUpdateDefaultPolicyWithSimpleKey() throws Exception {
        Policy variedPolicy = policyLoader.loadPolicy("src/test/resources/data/simpleset-policy.yaml");

        assertNotNull(variedPolicy);

        assertThat(variedPolicy.getName(), is("anotherName"));

        assertThat(variedPolicy.getCar().getMake(), is("Ford"));
        assertThat(variedPolicy.getCar().getModel(), is("Focus"));
        assertThat(variedPolicy.getCar().getYear(), is(2011));
    }

    @Test
    public void shouldUpdateDefaultPolicyWithMoreThanOneKey() throws Exception {
        Policy variedPolicy = policyLoader.loadPolicy("src/test/resources/data/twofield-policy.yaml");

        assertNotNull(variedPolicy);

        assertThat(variedPolicy.getName(), is("someOtherName"));
        assertThat(variedPolicy.getEmail(), is("abc@example.com"));

        assertThat(variedPolicy.getCar().getMake(), is("Ford"));
        assertThat(variedPolicy.getCar().getModel(), is("Focus"));
        assertThat(variedPolicy.getCar().getYear(), is(2011));
    }

    @Test
    public void shouldUpdateDefaultPolicyWithComposedKeys() throws Exception {
        Policy variedPolicy = policyLoader.loadPolicy("src/test/resources/data/complex-policy.yaml");

        assertNotNull(variedPolicy);

        assertThat(variedPolicy.getDrivers().get(0).getFirstName(), is("Bob"));
        assertThat(variedPolicy.getDrivers().get(0).getLastName(), is("Johnson"));
        assertThat(variedPolicy.getDrivers().get(0).getAge(), is(56));
        assertThat(variedPolicy.getDrivers().get(0).getAddress().getSuburb(), is("Carlton"));

        assertThat(variedPolicy.getCar().getMake(), is("Ford"));
        assertThat(variedPolicy.getCar().getModel(), is("Focus"));
        assertThat(variedPolicy.getCar().getYear(), is(2011));
    }

    @Test
    public void shouldUpdateDefaultPolicyWithMultipleNodes() throws Exception {
        Policy variedPolicy = policyLoader.loadPolicy("src/test/resources/data/multinode-policy.yaml");

        assertNotNull(variedPolicy);

        assertThat(variedPolicy.getDrivers().get(0).getFirstName(), is("Bob"));
        assertThat(variedPolicy.getDrivers().get(1).getFirstName(), is("John"));

        assertThat(variedPolicy.getCar().getMake(), is("Ford"));
        assertThat(variedPolicy.getCar().getModel(), is("Focus"));
        assertThat(variedPolicy.getCar().getYear(), is(2011));
    }
}
