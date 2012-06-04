package com.thoughtworks;

import java.util.List;

public class Policy {

    private String name;
    private String email;
    private List<Driver> drivers;
    private Car car;

    // Needed for SnakeYAML to construct object from yaml
    public Policy() {
    }

//    public Policy(String name, String email, List<Driver> drivers, Car car) {
//        this.name = name;
//        this.email = email;
//        this.drivers = drivers;
//        this.car = car;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
