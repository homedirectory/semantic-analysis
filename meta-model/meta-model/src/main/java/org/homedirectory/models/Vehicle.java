package org.homedirectory.models;

import org.homedirectory.annotations.GenerateMeta;

@GenerateMeta
public class Vehicle {
    private String color;
    private Insurance insurance;
    
    public Vehicle(String color, Insurance insurance) {
        this.color = color;
        this.insurance = insurance;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
