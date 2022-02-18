package org.homedirectory.models;

import org.homedirectory.annotations.GenerateMeta;

@GenerateMeta
public class Person extends TheGreatEntity {
    private String name;
    private int age;
    private Vehicle vehicle;
    private House house;
    
    public Person(String name, int age, Vehicle vehicle) {
        this.name = name;
        this.age = age;
        this.vehicle = vehicle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public House getHouse() {
        return house;
    }

    public void setAge(House House) {
        this.house = House;
    }
}
