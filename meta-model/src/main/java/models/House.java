package main.java.models;

public class House {
    private int area;
    private Insurance insurance;
    
    public House(int area, Insurance insurance) {
        this.area = area;
        this.insurance = insurance;
    }

    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        this.area = area;
    }
    public Insurance getInsurance() {
        return insurance;
    }
    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
