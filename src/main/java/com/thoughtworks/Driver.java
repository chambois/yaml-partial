package com.thoughtworks;

public class Driver {

    private String firstName;
    private String lastName;
    private int age;
    private Address address;

    // Needed for SnakeYAML to construct object from yaml
    public Driver() {
    }

//    public Driver(String firstName, String lastName, int age, Address address) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//        this.address = address;
//    }

    public Driver(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
