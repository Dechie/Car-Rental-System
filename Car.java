package com.example.rentalsystem;
public class Car {
    private String make;
    private String model;
    private boolean available;
    
    public Car(String make, String model, boolean available) {
        this.make = make;
        this.model = model;
        this.available = available;
    }
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
