package com.example.rentalsystem;

import  com.example.rentalsystem.*;
import java.util.Date;

public class Customer extends User {
    private Car rented;
    private Date rentStartDate;
    private Date rentDueDate;

    public Customer(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }
    public Car getRented() {
        return rented;
    }
    public void setRented(Car rented) {
        this.rented = rented;
    }
    public void setRentStartDate(Date rentStartDate) {
        this.rentStartDate = rentStartDate;
    }


    public void setRentDueDate(Date rentDueDate) {
        this.rentDueDate = rentDueDate;
    }

}
